package com.example.budgetspendingapp

import android.app.Application
import com.example.budgetspendingapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BudgetSpendingApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BudgetSpendingApp)
            modules(appModule)
        }
    }
}
