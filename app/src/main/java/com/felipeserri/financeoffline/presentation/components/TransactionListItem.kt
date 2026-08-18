package com.felipeserri.financeoffline.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.felipeserri.financeoffline.domain.model.TransactionType
import com.felipeserri.financeoffline.presentation.dashboard.TransactionWithCategory
import com.felipeserri.financeoffline.presentation.util.centsToCurrencyString
import com.felipeserri.financeoffline.ui.theme.FinanceTheme
import com.felipeserri.financeoffline.ui.theme.Spacing

@Composable
fun TransactionListItem(item: TransactionWithCategory, modifier: Modifier = Modifier) {
    val isExpense = item.transaction.type == TransactionType.EXPENSE
    val amountColor = if (isExpense) FinanceTheme.extendedColors.expense else FinanceTheme.extendedColors.income
    val sign = if (isExpense) "-" else "+"

    androidx.compose.foundation.layout.Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.transaction.description, style = MaterialTheme.typography.bodyLarge)
            androidx.compose.foundation.layout.Spacer(Modifier.height(Spacing.xs))
            item.category?.let { CategoryChip(name = it.name, colorHex = it.color) }
        }
        Text(
            text = "$sign${item.transaction.amount.centsToCurrencyString()}",
            style = MaterialTheme.typography.titleMedium,
            color = amountColor
        )
    }
}