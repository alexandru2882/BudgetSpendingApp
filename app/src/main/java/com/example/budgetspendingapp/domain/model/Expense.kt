package com.example.budgetspendingapp.domain.model

import java.time.LocalDate

data class Expense(
    val id: Int = 0,
    val amount: Double,
    val category: String,
    val date: LocalDate,
    val description: String
)
