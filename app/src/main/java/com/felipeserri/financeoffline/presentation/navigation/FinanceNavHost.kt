package com.felipeserri.financeoffline.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.felipeserri.financeoffline.presentation.categories.CategoriesScreen
import com.felipeserri.financeoffline.presentation.dashboard.DashboardScreen
import com.felipeserri.financeoffline.presentation.settings.SettingsScreen
import com.felipeserri.financeoffline.presentation.statistics.StatisticsScreen
import com.felipeserri.financeoffline.presentation.transactions.AddEditTransactionScreen
import com.felipeserri.financeoffline.presentation.transactions.TransactionsScreen

@Composable
fun FinanceNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Dashboard,
        modifier = modifier
    ) {
        composable<Destination.Dashboard> {
            DashboardScreen()
        }
        composable<Destination.Transactions> {
            TransactionsScreen(
                onAddTransaction = { navController.navigate(Destination.AddEditTransaction()) },
                onEditTransaction = { id -> navController.navigate(Destination.AddEditTransaction(transactionId = id)) }
            )
        }
        composable<Destination.AddEditTransaction> {
            AddEditTransactionScreen(
                onSaved = { navController.popBackStack() }
            )
        }
        composable<Destination.Categories> {
            CategoriesScreen()
        }
        composable<Destination.Statistics> {
            StatisticsScreen()
        }
        composable<Destination.Settings> {
            SettingsScreen()
        }
    }
}