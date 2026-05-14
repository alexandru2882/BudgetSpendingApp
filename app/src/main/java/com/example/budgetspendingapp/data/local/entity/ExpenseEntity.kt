package com.example.budgetspendingapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budgetspendingapp.domain.model.Expense
import java.time.LocalDate

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val category: String,
    val dateEpochDay: Long,
    val description: String
)

fun ExpenseEntity.toDomainModel(): Expense {
    return Expense(
        id = id,
        amount = amount,
        category = category,
        date = LocalDate.ofEpochDay(dateEpochDay),
        description = description
    )
}

fun Expense.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = id,
        amount = amount,
        category = category,
        dateEpochDay = date.toEpochDay(),
        description = description
    )
}
