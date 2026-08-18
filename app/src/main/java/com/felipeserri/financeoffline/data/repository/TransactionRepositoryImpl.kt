package com.felipeserri.financeoffline.data.repository

import com.felipeserri.financeoffline.data.local.dao.TransactionDao
import com.felipeserri.financeoffline.data.mapper.toDomain
import com.felipeserri.financeoffline.data.mapper.toEntity
import com.felipeserri.financeoffline.domain.model.Transaction
import com.felipeserri.financeoffline.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getAll(): Flow<List<Transaction>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override fun getByPeriod(startDate: Long, endDate: Long): Flow<List<Transaction>> =
        dao.getByPeriod(startDate, endDate).map { list -> list.map { it.toDomain() } }

    override suspend fun getById(id: String): Transaction? =
        dao.getById(id)?.toDomain()

    override suspend fun add(transaction: Transaction) {
        dao.insert(transaction.toEntity())
    }

    override suspend fun update(transaction: Transaction) {
        dao.update(transaction.toEntity())
    }

    override suspend fun delete(transaction: Transaction) {
        dao.delete(transaction.toEntity())
    }
}