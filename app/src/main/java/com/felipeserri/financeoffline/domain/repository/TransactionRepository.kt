package com.felipeserri.financeoffline.domain.repository

import com.felipeserri.financeoffline.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAll(): Flow<List<Transaction>>
    fun getByPeriod(startDate: Long, endDate: Long): Flow<List<Transaction>>
    suspend fun getById(id: String): Transaction?
    suspend fun add(transaction: Transaction)
    suspend fun update(transaction: Transaction)
    suspend fun delete(transaction: Transaction)
}