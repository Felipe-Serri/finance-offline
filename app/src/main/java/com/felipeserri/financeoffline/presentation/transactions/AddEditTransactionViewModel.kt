package com.felipeserri.financeoffline.presentation.transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.felipeserri.financeoffline.domain.model.Category
import com.felipeserri.financeoffline.domain.model.SyncStatus
import com.felipeserri.financeoffline.domain.model.Transaction
import com.felipeserri.financeoffline.domain.model.TransactionType
import com.felipeserri.financeoffline.domain.usecase.AddTransactionUseCase
import com.felipeserri.financeoffline.domain.usecase.DeleteTransactionUseCase
import com.felipeserri.financeoffline.domain.usecase.GetCategoriesUseCase
import com.felipeserri.financeoffline.domain.usecase.GetTransactionByIdUseCase
import com.felipeserri.financeoffline.domain.usecase.UpdateTransactionUseCase
import com.felipeserri.financeoffline.presentation.Resource
import com.felipeserri.financeoffline.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class TransactionFormState(
    val description: String = "",
    val amountText: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val categoryId: String? = null,
    val date: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis()
)

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val editingId = savedStateHandle.toRoute<Destination.AddEditTransaction>().transactionId
    val isEditing = editingId != null

    val categories = getCategoriesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList<Category>())

    var formState by mutableStateOf(TransactionFormState())
        private set

    var saveResult by mutableStateOf<Resource<Unit>?>(null)
        private set

    init {
        if (editingId != null) {
            viewModelScope.launch {
                getTransactionByIdUseCase(editingId)?.let { transaction ->
                    formState = TransactionFormState(
                        description = transaction.description,
                        amountText = (transaction.amount / 100.0).toString(),
                        type = transaction.type,
                        categoryId = transaction.categoryId,
                        date = transaction.date,
                        createdAt = transaction.createdAt
                    )
                }
            }
        }
    }

    fun onDescriptionChange(value: String) { formState = formState.copy(description = value) }
    fun onAmountChange(value: String) { formState = formState.copy(amountText = value) }
    fun onTypeChange(value: TransactionType) { formState = formState.copy(type = value, categoryId = null) }
    fun onCategoryChange(value: String) { formState = formState.copy(categoryId = value) }

    fun save() {
        val amountCents = formState.amountText.replace(",", ".").toDoubleOrNull()?.let { (it * 100).toLong() }
        val categoryId = formState.categoryId

        if (amountCents == null || amountCents <= 0 || categoryId == null || formState.description.isBlank()) {
            saveResult = Resource.Error("Preencha descrição, valor e categoria corretamente")
            return
        }

        val transaction = Transaction(
            id = editingId ?: UUID.randomUUID().toString(),
            type = formState.type,
            amount = amountCents,
            description = formState.description,
            categoryId = categoryId,
            date = formState.date,
            createdAt = formState.createdAt,
            updatedAt = System.currentTimeMillis(),
            syncStatus = SyncStatus.PENDING
        )

        viewModelScope.launch {
            saveResult = Resource.Loading
            try {
                if (editingId == null) addTransactionUseCase(transaction) else updateTransactionUseCase(transaction)
                saveResult = Resource.Success(Unit)
            } catch (e: IllegalArgumentException) {
                saveResult = Resource.Error(e.message ?: "Erro ao salvar")
            }
        }
    }

    fun delete(onDeleted: () -> Unit) {
        val id = editingId ?: return
        viewModelScope.launch {
            getTransactionByIdUseCase(id)?.let { deleteTransactionUseCase(it) }
            onDeleted()
        }
    }
}