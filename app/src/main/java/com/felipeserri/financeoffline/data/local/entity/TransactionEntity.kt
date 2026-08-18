package com.felipeserri.financeoffline.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.felipeserri.financeoffline.domain.model.SyncStatus
import com.felipeserri.financeoffline.domain.model.TransactionType

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("categoryId"), Index("date")]
)
data class TransactionEntity(
    @PrimaryKey
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