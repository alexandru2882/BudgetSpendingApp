package com.example.budgetspendingapp.di

import androidx.room.Room
import com.example.budgetspendingapp.data.local.database.AppDatabase
import com.example.budgetspendingapp.data.repository.ExpenseRepositoryImpl
import com.example.budgetspendingapp.domain.repository.ExpenseRepository
import com.example.budgetspendingapp.ui.analytics.AnalyticsViewModel
import com.example.budgetspendingapp.ui.dashboard.DashboardViewModel
import com.example.budgetspendingapp.ui.logging.ExpenseLoggingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    single { get<AppDatabase>().expenseDao() }
}

val repositoryModule = module {
    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }
    viewModel { ExpenseLoggingViewModel(get()) }
    viewModel { AnalyticsViewModel(get()) }
}

val appModule = listOf(databaseModule, repositoryModule, viewModelModule)
