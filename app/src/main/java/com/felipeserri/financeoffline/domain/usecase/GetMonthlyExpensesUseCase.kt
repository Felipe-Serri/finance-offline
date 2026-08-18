package com.felipeserri.financeoffline.domain.usecase

import com.felipeserri.financeoffline.domain.model.TransactionType
import com.felipeserri.financeoffline.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMonthlyExpensesUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(startOfMonth: Long, endOfMonth: Long): Flow<Long> =
        repository.getByPeriod(startOfMonth, endOfMonth).map { transactions ->
            transactions
                .filter { it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
        }
}