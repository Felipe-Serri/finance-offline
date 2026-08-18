package com.felipeserri.financeoffline

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.felipeserri.financeoffline.data.local.dao.CategoryDao
import com.felipeserri.financeoffline.data.local.dao.TransactionDao
import com.felipeserri.financeoffline.data.local.entity.CategoryEntity
import com.felipeserri.financeoffline.data.local.entity.TransactionEntity
import com.felipeserri.financeoffline.domain.model.SyncStatus
import com.felipeserri.financeoffline.domain.model.TransactionType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var transactionDao: TransactionDao
    @Inject lateinit var categoryDao: CategoryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val category = CategoryEntity(
                id = UUID.randomUUID().toString(),
                name = "Alimentação",
                type = TransactionType.EXPENSE,
                icon = "restaurant",
                color = "#FF5722"
            )
            categoryDao.insert(category)

            val transaction = TransactionEntity(
                id = UUID.randomUUID().toString(),
                type = TransactionType.EXPENSE,
                amount = 4590, // R$ 45,90 em centavos
                description = "Almoço",
                categoryId = category.id,
                date = System.currentTimeMillis(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                syncStatus = SyncStatus.PENDING
            )
            transactionDao.insert(transaction)

            transactionDao.getAll().collect { list ->
                Log.d("Room", "Transações no banco: $list")
            }
        }

        setContent {
            // conteúdo existente do template continua aqui
        }
    }
}