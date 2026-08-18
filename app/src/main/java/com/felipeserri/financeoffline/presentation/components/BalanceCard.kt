package com.felipeserri.financeoffline.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.felipeserri.financeoffline.presentation.util.centsToCurrencyString
import com.felipeserri.financeoffline.ui.theme.FinanceTheme
import com.felipeserri.financeoffline.ui.theme.Spacing

@Composable
fun BalanceCard(balanceCents: Long, modifier: Modifier = Modifier) {
    val color = if (balanceCents < 0) FinanceTheme.extendedColors.expense else FinanceTheme.extendedColors.income

    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(Spacing.lg)) {
            Text(
                text = "Saldo atual",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            androidx.compose.foundation.layout.Spacer(Modifier.height(Spacing.xs))
            Text(
                text = balanceCents.centsToCurrencyString(),
                style = MaterialTheme.typography.displaySmall,
                color = color
            )
        }
    }
}