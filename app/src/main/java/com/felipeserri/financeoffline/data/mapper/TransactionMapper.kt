package com.felipeserri.financeoffline.data.mapper

import com.felipeserri.financeoffline.data.local.entity.TransactionEntity
import com.felipeserri.financeoffline.domain.model.Transaction

fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    type = type,
    amount = amount,
    description = description,
    categoryId = categoryId,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt,
    syncStatus = syncStatus
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    type = type,
    amount = amount,
    description = description,
    categoryId = categoryId,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt,
    syncStatus = syncStatus
)