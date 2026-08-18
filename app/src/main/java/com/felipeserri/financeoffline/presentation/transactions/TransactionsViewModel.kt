package com.felipeserri.financeoffline.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipeserri.financeoffline.domain.model.Transaction
import com.felipeserri.financeoffline.domain.usecase.DeleteTransactionUseCase
import com.felipeserri.financeoffline.domain.usecase.GetCategoriesUseCase
import com.felipeserri.financeoffline.domain.usecase.GetTransactionsUseCase
import com.felipeserri.financeoffline.presentation.Resource
import com.felipeserri.financeoffline.presentation.components.TransactionWithCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    getTransactionsUseCase: GetTransactionsUseCase,
    getCategoriesUseCase: GetCategoriesUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    val uiState = combine(getTransactionsUseCase(), getCategoriesUseCase()) { transactions, categories ->
        val categoriesById = categories.associateBy { it.id }
        val list = transactions
            .sortedByDescending { it.date }
            .map { TransactionWithCategory(it, categoriesById[it.categoryId]) }

        if (list.isEmpty()) Resource.Empty else Resource.Success(list)
    }.catch { emit(Resource.Error(it.message ?: "Erro ao carregar transações")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Resource.Loading)

    fun onDelete(transaction: Transaction) {
        viewModelScope.launch { deleteTransactionUseCase(transaction) }
    }
}