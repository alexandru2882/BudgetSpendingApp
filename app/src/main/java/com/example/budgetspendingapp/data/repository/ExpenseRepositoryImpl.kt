package com.example.budgetspendingapp.data.repository

import com.example.budgetspendingapp.data.local.dao.ExpenseDao
import com.example.budgetspendingapp.data.local.entity.toDomainModel
import com.example.budgetspendingapp.data.local.entity.toEntity
import com.example.budgetspendingapp.domain.model.Expense
import com.example.budgetspendingapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth

class ExpenseRepositoryImpl(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override fun getAllExpenses(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun insertExpense(expense: Expense) {
        expenseDao.insertExpense(expense.toEntity())
    }

    override suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(expense.toEntity())
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense.toEntity())
    }

    override suspend fun getExpenseById(id: Int): Expense? {
        return expenseDao.getExpenseById(id)?.toDomainModel()
    }

    override fun getTotalSpendingForMonth(year: Int, month: Int): Flow<Double> {
        val yearMonth = YearMonth.of(year, month)
        val start = yearMonth.atDay(1).toEpochDay()
        val end = yearMonth.atEndOfMonth().toEpochDay()
        return expenseDao.getTotalSpendingForRange(start, end).map { it ?: 0.0 }
    }
}
