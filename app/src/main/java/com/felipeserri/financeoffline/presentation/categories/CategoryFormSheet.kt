package com.felipeserri.financeoffline.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.felipeserri.financeoffline.domain.model.TransactionType
import com.felipeserri.financeoffline.presentation.util.categoryColorOptions
import com.felipeserri.financeoffline.presentation.util.categoryIconOptions
import com.felipeserri.financeoffline.presentation.util.colorForHex
import com.felipeserri.financeoffline.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFormSheet(
    state: CategoryFormState,
    onNameChange: (String) -> Unit,
    onTypeChange: (TransactionType) -> Unit,
    onIconChange: (String) -> Unit,
    onColorChange: (String) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.padding(Spacing.lg).navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            Text(
                text = if (state.id == null) "Nova categoria" else "Editar categoria",
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedTextField(
                value = state.name,
                onValueChange = onNameChange,
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )

            SingleChoiceSegmentedButtonRow {
                TransactionType.entries.forEachIndexed { index, type ->
                    SegmentedButton(
                        selected = state.type == type,
                        onClick = { onTypeChange(type) },
                        shape = SegmentedButtonDefaults.itemShape(index, TransactionType.entries.size)
                    ) {
                        Text(if (type == TransactionType.INCOME) "Receita" else "Despesa")
                    }
                }
            }

            Text("Ícone", style = MaterialTheme.typography.titleMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                items(categoryIconOptions) { (name, icon) ->
                    IconOption(icon = icon, selected = state.icon == name, onClick = { onIconChange(name) })
                }
            }

            Text("Cor", style = MaterialTheme.typography.titleMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                items(categoryColorOptions) { hex ->
                    ColorOption(colorHex = hex, selected = state.color == hex, onClick = { onColorChange(hex) })
                }
            }

            Button(onClick = onSave, enabled = state.name.isNotBlank(), modifier = Modifier.fillMaxWidth()) {
                Text("Salvar")
            }
        }
    }
}

@Composable
private fun IconOption(icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.size(48.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null)
        }
    }
}

@Composable
private fun ColorOption(colorHex: String, selected: Boolean, onClick: () -> Unit) {
    val color = remember(colorHex) { colorForHex(colorHex) }
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .then(
                if (selected) Modifier.border(3.dp, MaterialTheme.colorScheme.onSurface, CircleShape) else Modifier
            )
            .clickable(onClick = onClick)
    )
}