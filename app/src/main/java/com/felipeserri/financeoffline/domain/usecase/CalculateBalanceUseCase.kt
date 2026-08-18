package com.felipeserri.financeoffline.domain.usecase

import com.felipeserri.financeoffline.domain.model.TransactionType
import com.felipeserri.financeoffline.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CalculateBalanceUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<Long> =
        repository.getAll().map { transactions ->
            transactions.sumOf { transaction ->
                when (transaction.type) {
                    TransactionType.INCOME -> transaction.amount
                    TransactionType.EXPENSE -> -transaction.amount
                }
            }
        }
}