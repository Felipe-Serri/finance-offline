package com.felipeserri.financeoffline.di

import javax.inject.Inject

class HiltSmokeTest @Inject constructor() {
    fun ping(): String = "Hilt configurado corretamente"
}