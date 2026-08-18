package com.felipeserri.financeoffline.domain.usecase

import com.felipeserri.financeoffline.domain.model.TransactionType
import com.felipeserri.financeoffline.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExpensesByCategoryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<Map<String, Long>> =
        repository.getAll().map { transactions ->
            transactions
                .filter { it.type == TransactionType.EXPENSE }
                .groupBy { it.categoryId }
                .mapValues { (_, txs) -> txs.sumOf { it.amount } }
        }
}