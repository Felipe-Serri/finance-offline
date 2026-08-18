package com.felipeserri.financeoffline.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.felipeserri.financeoffline.data.local.dao.CategoryDao
import com.felipeserri.financeoffline.data.local.dao.TransactionDao
import com.felipeserri.financeoffline.data.local.entity.CategoryEntity
import com.felipeserri.financeoffline.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class FinanceDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
}