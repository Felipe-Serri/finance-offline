package com.felipeserri.financeoffline.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipeserri.financeoffline.presentation.Resource
import com.felipeserri.financeoffline.presentation.components.EmptyState
import com.felipeserri.financeoffline.presentation.components.ErrorState
import com.felipeserri.financeoffline.presentation.components.LoadingState
import com.felipeserri.financeoffline.presentation.components.TransactionListItem
import com.felipeserri.financeoffline.presentation.components.TransactionWithCategory
import com.felipeserri.financeoffline.ui.theme.Spacing

@Composable
fun TransactionsScreen(
    onAddTransaction: () -> Unit,
    onEditTransaction: (String) -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTransaction) {
                Icon(Icons.Default.Add, contentDescription = "Nova transação")
            }
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is Resource.Loading -> LoadingState(modifier = Modifier.padding(innerPadding).fillMaxSize())
            is Resource.Error -> ErrorState(state.message, Modifier.padding(innerPadding).fillMaxSize())
            is Resource.Empty -> EmptyState(
                "Nenhuma transação ainda. Toque em + pra começar.",
                Modifier.padding(innerPadding).fillMaxSize()
            )
            is Resource.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding).fillMaxSize(),
                    contentPadding = PaddingValues(Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    items(state.data, key = { it.transaction.id }) { item ->
                        SwipeToDeleteItem(
                            item = item,
                            onClick = { onEditTransaction(item.transaction.id) },
                            onDelete = { viewModel.onDelete(item.transaction) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeToDeleteItem(
    item: TransactionWithCategory,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.errorContainer, MaterialTheme.shapes.medium)
                    .padding(horizontal = Spacing.md),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Excluir",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    ) {
        Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
            TransactionListItem(
                item = item,
                modifier = Modifier.padding(Spacing.md),
                showSyncStatus = true
            )
        }
    }
}