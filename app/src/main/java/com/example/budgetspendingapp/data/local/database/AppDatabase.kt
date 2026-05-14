package com.example.budgetspendingapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budgetspendingapp.data.local.dao.ExpenseDao
import com.example.budgetspendingapp.data.local.entity.ExpenseEntity

@Database(entities = [ExpenseEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        const val DATABASE_NAME = "budget_spending_db"
    }
}
