package com.felipeserri.financeoffline.domain.usecase

import com.felipeserri.financeoffline.domain.model.Category
import com.felipeserri.financeoffline.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> = repository.getAll()
}