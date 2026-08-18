package com.felipeserri.financeoffline.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.felipeserri.financeoffline.domain.model.TransactionType

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: TransactionType,
    val icon: String,
    val color: String
)