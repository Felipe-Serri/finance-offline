package com.felipeserri.financeoffline.presentation.transactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TransactionsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Transactions — Fase 8")
    }
}

@Composable
fun AddEditTransactionScreen(transactionId: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(if (transactionId == null) "Nova transação — Fase 8" else "Editar transação $transactionId — Fase 8")
    }
}