package com.example.budgetspendingapp.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute : NavKey {
    @Serializable
    data object Dashboard : NavRoute()
    
    @Serializable
    data object Logging : NavRoute()
    
    @Serializable
    data object Analytics : NavRoute()
}
