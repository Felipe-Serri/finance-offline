package com.felipeserri.financeoffline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.felipeserri.financeoffline.presentation.navigation.FinanceApp
import com.felipeserri.financeoffline.ui.theme.FinanceOfflineTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceOfflineTheme {
                FinanceApp()
            }
        }
    }
}