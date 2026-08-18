package com.felipeserri.financeoffline.presentation.util

import java.text.NumberFormat
import java.util.Locale

fun Long.centsToCurrencyString(): String {
    val amount = this / 100.0
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formatter.format(amount)
}