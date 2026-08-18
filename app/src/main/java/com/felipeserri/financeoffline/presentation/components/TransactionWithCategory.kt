package com.felipeserri.financeoffline.presentation.components

import com.felipeserri.financeoffline.domain.model.Category
import com.felipeserri.financeoffline.domain.model.Transaction

data class TransactionWithCategory(
    val transaction: Transaction,
    val category: Category?
)