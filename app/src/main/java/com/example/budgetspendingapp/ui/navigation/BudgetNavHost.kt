package com.example.budgetspendingapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.budgetspendingapp.ui.analytics.AnalyticsScreen
import com.example.budgetspendingapp.ui.dashboard.DashboardScreen
import com.example.budgetspendingapp.ui.logging.ExpenseLoggingScreen

@OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)
@Composable
fun BudgetNavHost() {
    val dashboardBackStack = rememberNavBackStack(NavRoute.Dashboard)
    val analyticsBackStack = rememberNavBackStack(NavRoute.Analytics)
    
    var currentTopLevel by remember { mutableStateOf<NavRoute>(NavRoute.Dashboard) }
    
    val activeBackStack = if (currentTopLevel == NavRoute.Dashboard) dashboardBackStack else analyticsBackStack

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            item(
                selected = currentTopLevel == NavRoute.Dashboard,
                onClick = { currentTopLevel = NavRoute.Dashboard },
                icon = { Icon(Icons.Rounded.Dashboard, contentDescription = "Dashboard") },
                label = { Text("Dashboard") }
            )
            item(
                selected = currentTopLevel == NavRoute.Analytics,
                onClick = { currentTopLevel = NavRoute.Analytics },
                icon = { Icon(Icons.Rounded.Analytics, contentDescription = "Analytics") },
                label = { Text("Analytics") }
            )
        }
    ) {
        NavDisplay(
            backStack = activeBackStack,
            onBack = { 
                if (activeBackStack.size > 1) {
                    activeBackStack.removeAt(activeBackStack.size - 1)
                }
            }
        ) { key ->
            when (key) {
                is NavRoute.Dashboard -> NavEntry(key) {
                    DashboardScreen(
                        onAddExpenseClick = { dashboardBackStack.add(NavRoute.Logging) }
                    )
                }
                is NavRoute.Logging -> NavEntry(key) {
                    ExpenseLoggingScreen(
                        onBackClick = { dashboardBackStack.removeAt(dashboardBackStack.size - 1) }
                    )
                }
                is NavRoute.Analytics -> NavEntry(key) {
                    AnalyticsScreen()
                }
                else -> NavEntry(key) { Text("Unknown Route") }
            }
        }
    }
}
