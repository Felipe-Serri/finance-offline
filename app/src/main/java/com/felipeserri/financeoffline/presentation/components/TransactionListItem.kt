package com.felipeserri.financeoffline.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.felipeserri.financeoffline.domain.model.SyncStatus
import com.felipeserri.financeoffline.domain.model.TransactionType
import com.felipeserri.financeoffline.presentation.util.centsToCurrencyString
import com.felipeserri.financeoffline.ui.theme.FinanceTheme
import com.felipeserri.financeoffline.ui.theme.Spacing

@Composable
fun TransactionListItem(
    item: TransactionWithCategory,
    modifier: Modifier = Modifier,
    showSyncStatus: Boolean = false
) {
    val isExpense = item.transaction.type == TransactionType.EXPENSE
    val amountColor = if (isExpense) FinanceTheme.extendedColors.expense else FinanceTheme.extendedColors.income
    val sign = if (isExpense) "-" else "+"

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = item.transaction.description, style = MaterialTheme.typography.bodyLarge)
                if (showSyncStatus) {
                    Spacer(Modifier.width(Spacing.xs))
                    SyncStatusIcon(item.transaction.syncStatus)
                }
            }
            Spacer(Modifier.height(Spacing.xs))
            item.category?.let { CategoryChip(name = it.name, colorHex = it.color) }
        }
        Text(
            text = "$sign${item.transaction.amount.centsToCurrencyString()}",
            style = MaterialTheme.typography.titleMedium,
            color = amountColor
        )
    }
}

@Composable
private fun SyncStatusIcon(status: SyncStatus, modifier: Modifier = Modifier) {
    val (icon, tint, description) = when (status) {
        SyncStatus.SYNCED -> Triple(Icons.Default.CloudDone, MaterialTheme.colorScheme.primary, "Sincronizado")
        SyncStatus.PENDING -> Triple(Icons.Default.CloudQueue, MaterialTheme.colorScheme.onSurfaceVariant, "Pendente de sincronização")
        SyncStatus.ERROR -> Triple(Icons.Default.CloudOff, MaterialTheme.colorScheme.error, "Erro ao sincronizar")
    }
    Icon(imageVector = icon, contentDescription = description, tint = tint, modifier = modifier.size(16.dp))
}