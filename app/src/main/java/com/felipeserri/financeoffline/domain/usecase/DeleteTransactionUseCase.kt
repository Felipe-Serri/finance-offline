package com.felipeserri.financeoffline.domain.usecase

import com.felipeserri.financeoffline.domain.model.Transaction
import com.felipeserri.financeoffline.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.delete(transaction)
    }
}