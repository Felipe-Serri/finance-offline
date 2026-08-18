package com.felipeserri.financeoffline.domain.repository

import com.felipeserri.financeoffline.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAll(): Flow<List<Category>>
    suspend fun getById(id: String): Category?
    suspend fun add(category: Category)
    suspend fun update(category: Category)
    suspend fun delete(category: Category)
}