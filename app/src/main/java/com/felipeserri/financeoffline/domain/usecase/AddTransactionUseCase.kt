package com.felipeserri.financeoffline.domain.usecase

import com.felipeserri.financeoffline.domain.model.Transaction
import com.felipeserri.financeoffline.domain.repository.TransactionRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        require(transaction.amount > 0) { "O valor da transação deve ser maior que zero" }
        require(transaction.description.isNotBlank()) { "A descrição não pode estar vazia" }
        repository.add(transaction)
    }
}