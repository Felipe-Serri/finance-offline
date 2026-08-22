package com.felipeserri.financeoffline.presentation.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipeserri.financeoffline.domain.model.Category
import com.felipeserri.financeoffline.domain.model.TransactionType
import com.felipeserri.financeoffline.presentation.Resource
import com.felipeserri.financeoffline.presentation.components.EmptyState
import com.felipeserri.financeoffline.presentation.components.ErrorState
import com.felipeserri.financeoffline.presentation.components.LoadingState
import com.felipeserri.financeoffline.presentation.util.colorForHex
import com.felipeserri.financeoffline.presentation.util.iconForName
import com.felipeserri.financeoffline.ui.theme.Spacing

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formState = viewModel.formState
    val deleteError = viewModel.deleteError

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::openNewCategoryForm) {
                Icon(Icons.Default.Add, contentDescription = "Nova categoria")
            }
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is Resource.Loading -> LoadingState(Modifier.padding(innerPadding).fillMaxSize())
            is Resource.Error -> ErrorState(state.message, Modifier.padding(innerPadding).fillMaxSize())
            is Resource.Empty -> EmptyState(
                "Nenhuma categoria ainda. Toque em + pra criar.",
                Modifier.padding(innerPadding).fillMaxSize()
            )
            is Resource.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding).fillMaxSize(),
                    contentPadding = PaddingValues(Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    items(state.data, key = { it.id }) { category ->
                        CategoryRow(
                            category = category,
                            onClick = { viewModel.openEditCategoryForm(category) },
                            onDelete = { viewModel.delete(category) }
                        )
                    }
                }
            }
        }
    }

    if (formState != null) {
        CategoryFormSheet(
            state = formState,
            onNameChange = viewModel::onNameChange,
            onTypeChange = viewModel::onTypeChange,
            onIconChange = viewModel::onIconChange,
            onColorChange = viewModel::onColorChange,
            onSave = viewModel::save,
            onDismiss = viewModel::closeForm
        )
    }

    if (deleteError != null) {
        AlertDialog(
            onDismissRequest = viewModel::dismissDeleteError,
            confirmButton = { TextButton(onClick = viewModel::dismissDeleteError) { Text("OK") } },
            title = { Text("Não foi possível excluir") },
            text = { Text(deleteError) }
        )
    }
}

@Composable
private fun CategoryRow(category: Category, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onClick)
                    .padding(Spacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(iconForName(category.icon), contentDescription = null, tint = colorForHex(category.color))
                Spacer(Modifier.width(Spacing.sm))
                Column {
                    Text(category.name, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = if (category.type == TransactionType.INCOME) "Receita" else "Despesa",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(onClick = onDelete, modifier = Modifier.padding(end = Spacing.sm)) {
                Icon(Icons.Default.Delete, contentDescription = "Excluir categoria")
            }
        }
    }
}