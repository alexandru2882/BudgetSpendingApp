package com.example.budgetspendingapp.domain.repository

import com.example.budgetspendingapp.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<Expense>>
    suspend fun insertExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
    suspend fun getExpenseById(id: Int): Expense?
    fun getTotalSpendingForMonth(year: Int, month: Int): Flow<Double>
}
