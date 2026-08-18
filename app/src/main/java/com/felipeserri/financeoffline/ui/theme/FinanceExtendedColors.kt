package com.felipeserri.financeoffline.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class FinanceExtendedColors(
    val income: Color,
    val expense: Color,
    val onIncome: Color,
    val onExpense: Color
)

val LightFinanceExtendedColors = FinanceExtendedColors(
    income = Color(0xFF2E7D32),
    expense = Color(0xFFC62828),
    onIncome = Color.White,
    onExpense = Color.White
)

val DarkFinanceExtendedColors = FinanceExtendedColors(
    income = Color(0xFF81C784),
    expense = Color(0xFFE57373),
    onIncome = Color(0xFF00390B),
    onExpense = Color(0xFF680003)
)

val LocalFinanceExtendedColors = staticCompositionLocalOf { LightFinanceExtendedColors }