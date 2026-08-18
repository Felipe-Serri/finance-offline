package com.felipeserri.financeoffline.data.repository

import com.felipeserri.financeoffline.data.local.dao.CategoryDao
import com.felipeserri.financeoffline.data.mapper.toDomain
import com.felipeserri.financeoffline.data.mapper.toEntity
import com.felipeserri.financeoffline.domain.model.Category
import com.felipeserri.financeoffline.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao
) : CategoryRepository {

    override fun getAll(): Flow<List<Category>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getById(id: String): Category? =
        dao.getById(id)?.toDomain()

    override suspend fun add(category: Category) {
        dao.insert(category.toEntity())
    }

    override suspend fun update(category: Category) {
        dao.update(category.toEntity())
    }

    override suspend fun delete(category: Category) {
        dao.delete(category.toEntity())
    }
}