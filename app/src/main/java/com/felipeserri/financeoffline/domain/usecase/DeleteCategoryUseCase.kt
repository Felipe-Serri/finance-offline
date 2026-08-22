package com.felipeserri.financeoffline.domain.usecase

import com.felipeserri.financeoffline.domain.model.Category
import com.felipeserri.financeoffline.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        repository.delete(category)
    }
}