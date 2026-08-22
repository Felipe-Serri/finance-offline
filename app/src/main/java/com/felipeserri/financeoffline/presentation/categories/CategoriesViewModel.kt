package com.felipeserri.financeoffline.presentation.categories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipeserri.financeoffline.domain.model.Category
import com.felipeserri.financeoffline.domain.model.CategoryInUseException
import com.felipeserri.financeoffline.domain.model.TransactionType
import com.felipeserri.financeoffline.domain.usecase.AddCategoryUseCase
import com.felipeserri.financeoffline.domain.usecase.DeleteCategoryUseCase
import com.felipeserri.financeoffline.domain.usecase.GetCategoriesUseCase
import com.felipeserri.financeoffline.domain.usecase.UpdateCategoryUseCase
import com.felipeserri.financeoffline.presentation.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class CategoryFormState(
    val id: String? = null,
    val name: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val icon: String = "category",
    val color: String = "#546E7A"
)

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : ViewModel() {

    val uiState = getCategoriesUseCase()
        .map<List<Category>, Resource<List<Category>>> { if (it.isEmpty()) Resource.Empty else Resource.Success(it) }
        .catch { emit(Resource.Error(it.message ?: "Erro ao carregar categorias")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Resource.Loading)

    var formState by mutableStateOf<CategoryFormState?>(null)
        private set

    var deleteError by mutableStateOf<String?>(null)
        private set

    fun openNewCategoryForm() { formState = CategoryFormState() }

    fun openEditCategoryForm(category: Category) {
        formState = CategoryFormState(
            id = category.id,
            name = category.name,
            type = category.type,
            icon = category.icon,
            color = category.color
        )
    }

    fun closeForm() { formState = null }
    fun onNameChange(value: String) { formState = formState?.copy(name = value) }
    fun onTypeChange(value: TransactionType) { formState = formState?.copy(type = value) }
    fun onIconChange(value: String) { formState = formState?.copy(icon = value) }
    fun onColorChange(value: String) { formState = formState?.copy(color = value) }

    fun save() {
        val state = formState ?: return
        if (state.name.isBlank()) return

        val category = Category(
            id = state.id ?: UUID.randomUUID().toString(),
            name = state.name,
            type = state.type,
            icon = state.icon,
            color = state.color
        )

        viewModelScope.launch {
            if (state.id == null) addCategoryUseCase(category) else updateCategoryUseCase(category)
            formState = null
        }
    }

    fun delete(category: Category) {
        viewModelScope.launch {
            try {
                deleteCategoryUseCase(category)
            } catch (e: CategoryInUseException) {
                deleteError = e.message
            }
        }
    }

    fun dismissDeleteError() { deleteError = null }
}