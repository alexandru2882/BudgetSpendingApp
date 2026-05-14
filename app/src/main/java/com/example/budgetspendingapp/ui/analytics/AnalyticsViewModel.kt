package com.example.budgetspendingapp.ui.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetspendingapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.*
import java.time.LocalDate

data class CategoryTotal(
    val category: String,
    val total: Double,
    val percentage: Float
)

class AnalyticsViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()

    val categoryTotals: StateFlow<List<CategoryTotal>> = _currentDate
        .flatMapLatest { date ->
            repository.getAllExpenses().map { expenses ->
                val filtered = expenses.filter { it.date.monthValue == date.monthValue && it.date.year == date.year }
                val totalMonth = filtered.sumOf { it.amount }
                if (totalMonth == 0.0) emptyList()
                else {
                    filtered.groupBy { it.category }
                        .map { (category, list) ->
                            val total = list.sumOf { it.amount }
                            CategoryTotal(
                                category = category,
                                total = total,
                                percentage = (total / totalMonth).toFloat()
                            )
                        }
                        .sortedByDescending { it.total }
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
