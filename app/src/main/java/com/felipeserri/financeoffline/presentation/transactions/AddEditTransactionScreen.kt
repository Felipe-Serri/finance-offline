package com.felipeserri.financeoffline.presentation.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipeserri.financeoffline.domain.model.Category
import com.felipeserri.financeoffline.domain.model.TransactionType
import com.felipeserri.financeoffline.presentation.Resource
import com.felipeserri.financeoffline.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTransactionScreen(
    onSaved: () -> Unit,
    viewModel: AddEditTransactionViewModel = hiltViewModel()
) {
    val formState = viewModel.formState
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val saveResult = viewModel.saveResult
    val filteredCategories = categories.filter { it.type == formState.type }

    LaunchedEffect(saveResult) {
        if (saveResult is Resource.Success) onSaved()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (viewModel.isEditing) "Editar transação" else "Nova transação") },
                actions = {
                    if (viewModel.isEditing) {
                        IconButton(onClick = { viewModel.delete(onSaved) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Excluir transação")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(Spacing.md).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            SingleChoiceSegmentedButtonRow {
                TransactionType.entries.forEachIndexed { index, type ->
                    SegmentedButton(
                        selected = formState.type == type,
                        onClick = { viewModel.onTypeChange(type) },
                        shape = SegmentedButtonDefaults.itemShape(index, TransactionType.entries.size)
                    ) {
                        Text(if (type == TransactionType.INCOME) "Receita" else "Despesa")
                    }
                }
            }

            OutlinedTextField(
                value = formState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formState.amountText,
                onValueChange = viewModel::onAmountChange,
                label = { Text("Valor (R$)") },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            CategoryDropdown(
                categories = filteredCategories,
                selectedCategoryId = formState.categoryId,
                onCategorySelected = viewModel::onCategoryChange
            )

            if (saveResult is Resource.Error) {
                Text(text = saveResult.message, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = viewModel::save,
                enabled = saveResult !is Resource.Loading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryDropdown(
    categories: List<Category>,
    selectedCategoryId: String?,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selected = categories.find { it.id == selectedCategoryId }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = selected?.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Categoria") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable).fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            if (categories.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("Nenhuma categoria — crie uma na aba Categorias") },
                    onClick = { expanded = false }
                )
            }
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = { onCategorySelected(category.id); expanded = false }
                )
            }
        }
    }
}