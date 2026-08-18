package com.felipeserri.financeoffline

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.felipeserri.financeoffline.domain.model.Category
import com.felipeserri.financeoffline.domain.model.SyncStatus
import com.felipeserri.financeoffline.domain.model.Transaction
import com.felipeserri.financeoffline.domain.model.TransactionType
import com.felipeserri.financeoffline.domain.repository.CategoryRepository
import com.felipeserri.financeoffline.domain.usecase.AddTransactionUseCase
import com.felipeserri.financeoffline.domain.usecase.CalculateBalanceUseCase
import com.felipeserri.financeoffline.domain.usecase.GetTransactionsUseCase
import com.felipeserri.financeoffline.ui.theme.FinanceOfflineTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var categoryRepository: CategoryRepository
    @Inject lateinit var addTransactionUseCase: AddTransactionUseCase
    @Inject lateinit var getTransactionsUseCase: GetTransactionsUseCase
    @Inject lateinit var calculateBalanceUseCase: CalculateBalanceUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val category = Category(
                id = UUID.randomUUID().toString(),
                name = "Alimentação",
                type = TransactionType.EXPENSE,
                icon = "restaurant",
                color = "#FF5722"
            )
            categoryRepository.add(category)

            addTransactionUseCase(
                Transaction(
                    id = UUID.randomUUID().toString(),
                    type = TransactionType.EXPENSE,
                    amount = 4590,
                    description = "Almoço",
                    categoryId = category.id,
                    date = System.currentTimeMillis(),
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    syncStatus = SyncStatus.PENDING
                )
            )

            launch {
                getTransactionsUseCase().collect { list ->
                    Log.d("Domain", "Transações (via Use Case): $list")
                }
            }
            launch {
                calculateBalanceUseCase().collect { balance ->
                    Log.d("Domain", "Saldo atual (centavos): $balance")
                }
            }
        }

        setContent {
            FinanceOfflineTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text(
                        text = "Finance Offline",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}