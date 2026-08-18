package com.felipeserri.financeoffline.domain.model

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Long,
    val description: String,
    val categoryId: String,
    val date: Long,
    val createdAt: Long,
    val updatedAt: Long,
    val syncStatus: SyncStatus
)