package com.example.budgetspendingapp.ui.logging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetspendingapp.domain.model.Expense
import com.example.budgetspendingapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ExpenseLoggingViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _category = MutableStateFlow("General")
    val category: StateFlow<String> = _category.asStateFlow()

    private val _date = MutableStateFlow(LocalDate.now())
    val date: StateFlow<LocalDate> = _date.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()

    fun onAmountChange(newAmount: String) {
        if (newAmount.all { it.isDigit() || it == '.' }) {
            _amount.value = newAmount
        }
    }

    fun onCategoryChange(newCategory: String) {
        _category.value = newCategory
    }

    fun onDateChange(newDate: LocalDate) {
        _date.value = newDate
    }

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    fun saveExpense() {
        val amountValue = _amount.value.toDoubleOrNull() ?: return
        viewModelScope.launch {
            val expense = Expense(
                amount = amountValue,
                category = _category.value,
                date = _date.value,
                description = _description.value
            )
            repository.insertExpense(expense)
            _saveSuccess.value = true
        }
    }

    fun resetSaveSuccess() {
        _saveSuccess.value = false
    }
}
