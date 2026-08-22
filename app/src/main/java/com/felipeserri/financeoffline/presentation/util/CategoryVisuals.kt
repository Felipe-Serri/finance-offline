package com.felipeserri.financeoffline.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

val categoryIconOptions: List<Pair<String, ImageVector>> = listOf(
    "restaurant" to Icons.Default.Restaurant,
    "directions_car" to Icons.Default.DirectionsCar,
    "home" to Icons.Default.Home,
    "shopping_cart" to Icons.Default.ShoppingCart,
    "local_hospital" to Icons.Default.LocalHospital,
    "school" to Icons.Default.School,
    "movie" to Icons.Default.Movie,
    "attach_money" to Icons.Default.AttachMoney,
    "category" to Icons.Default.Category
)

val categoryColorOptions = listOf(
    "#E53935", "#8E24AA", "#3949AB", "#039BE5", "#00897B",
    "#7CB342", "#FDD835", "#FB8C00", "#6D4C41", "#546E7A"
)

fun iconForName(name: String): ImageVector =
    categoryIconOptions.find { it.first == name }?.second ?: Icons.Default.Category

fun colorForHex(hex: String): Color =
    runCatching { Color(android.graphics.Color.parseColor(hex)) }.getOrDefault(Color.Gray)