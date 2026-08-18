package com.felipeserri.financeoffline.domain.usecase

import com.felipeserri.financeoffline.domain.model.Transaction
import com.felipeserri.financeoffline.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: String): Transaction? = repository.getById(id)
}