package com.example.budgetspendingapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetspendingapp.domain.model.Expense
import com.example.budgetspendingapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.*
import java.time.LocalDate

class DashboardViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()

    val totalSpending: StateFlow<Double> = _currentDate
        .flatMapLatest { date ->
            repository.getTotalSpendingForMonth(date.year, date.monthValue)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val recentExpenses: StateFlow<List<Expense>> = repository.getAllExpenses()
        .map { it.take(10) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
