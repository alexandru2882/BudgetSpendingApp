package com.example.budgetspendingapp.data.local.dao

import androidx.room.*
import com.example.budgetspendingapp.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY dateEpochDay DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getExpenseById(id: Int): ExpenseEntity?

    @Query("SELECT SUM(amount) FROM expenses WHERE dateEpochDay >= :startEpochDay AND dateEpochDay <= :endEpochDay")
    fun getTotalSpendingForRange(startEpochDay: Long, endEpochDay: Long): Flow<Double?>
}
