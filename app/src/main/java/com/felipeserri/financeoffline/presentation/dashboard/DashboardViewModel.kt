package com.felipeserri.financeoffline.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipeserri.financeoffline.domain.model.Category
import com.felipeserri.financeoffline.domain.model.Transaction
import com.felipeserri.financeoffline.domain.usecase.CalculateBalanceUseCase
import com.felipeserri.financeoffline.domain.usecase.GetCategoriesUseCase
import com.felipeserri.financeoffline.domain.usecase.GetTransactionsUseCase
import com.felipeserri.financeoffline.presentation.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.felipeserri.financeoffline.presentation.components.TransactionWithCategory


data class DashboardUiState(
    val balance: Resource<Long> = Resource.Loading,
    val recentTransactions: Resource<List<TransactionWithCategory>> = Resource.Loading
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    calculateBalanceUseCase: CalculateBalanceUseCase,
    getTransactionsUseCase: GetTransactionsUseCase,
    getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val balanceState = calculateBalanceUseCase()
        .map<Long, Resource<Long>> { Resource.Success(it) }
        .catch { emit(Resource.Error(it.message ?: "Erro ao calcular saldo")) }

    private val recentTransactionsState = combine(
        getTransactionsUseCase(),
        getCategoriesUseCase()
    ) { transactions, categories ->
        val categoriesById = categories.associateBy { it.id }
        val recent = transactions
            .sortedByDescending { it.date }
            .take(5)
            .map { transaction -> TransactionWithCategory(transaction, categoriesById[transaction.categoryId]) }

        if (recent.isEmpty()) Resource.Empty else Resource.Success(recent)
    }.catch { emit(Resource.Error(it.message ?: "Erro ao carregar transações")) }

    val uiState = combine(balanceState, recentTransactionsState) { balance, transactions ->
        DashboardUiState(balance = balance, recentTransactions = transactions)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashboardUiState()
    )
}