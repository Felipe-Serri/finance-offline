package com.felipeserri.financeoffline.domain.model

data class Category(
    val id: String,
    val name: String,
    val type: TransactionType,
    val icon: String,
    val color: String
)