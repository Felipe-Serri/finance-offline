package com.felipeserri.financeoffline.presentation.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipeserri.financeoffline.presentation.Resource
import com.felipeserri.financeoffline.presentation.components.BalanceCard
import com.felipeserri.financeoffline.presentation.components.EmptyState
import com.felipeserri.financeoffline.presentation.components.ErrorState
import com.felipeserri.financeoffline.presentation.components.LoadingState
import com.felipeserri.financeoffline.presentation.components.TransactionListItem
import com.felipeserri.financeoffline.ui.theme.Spacing

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            item {
                Text(text = "Finance Offline", style = MaterialTheme.typography.titleLarge)
            }

            item {
                AnimatedContent(
                    targetState = uiState.balance,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "balanceState"
                ) { balanceResource ->
                    when (balanceResource) {
                        is Resource.Loading -> LoadingState()
                        is Resource.Success -> BalanceCard(balanceCents = balanceResource.data)
                        is Resource.Error -> ErrorState(message = balanceResource.message)
                        is Resource.Empty -> EmptyState(message = "Nenhum dado ainda")
                    }
                }
            }

            item {
                Text(text = "Transações recentes", style = MaterialTheme.typography.titleMedium)
            }

            when (val recent = uiState.recentTransactions) {
                is Resource.Loading -> item { LoadingState() }
                is Resource.Error -> item { ErrorState(message = recent.message) }
                is Resource.Empty -> item {
                    EmptyState(message = "Nenhuma transação ainda. Toque em + pra começar.")
                }
                is Resource.Success -> {
                    items(recent.data, key = { it.transaction.id }) { transactionWithCategory ->
                        Card {
                            TransactionListItem(
                                item = transactionWithCategory,
                                modifier = Modifier.padding(Spacing.md)
                            )
                        }
                    }
                }
            }
        }
    }
}