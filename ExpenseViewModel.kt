package com.example.carcosts.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carcosts.data.local.dao.ExpenseDao
import com.example.carcosts.data.local.entity.ExpenseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseDao: ExpenseDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val carId: Int = checkNotNull(savedStateHandle["carId"]).toString().toInt()

    val expenses: StateFlow<List<ExpenseEntity>> = expenseDao.getExpensesForCar(carId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addExpense(amount: Double, category: String, mileage: Int, comment: String) {
        viewModelScope.launch {
            val expense = ExpenseEntity(
                carId = carId,
                date = System.currentTimeMillis(),
                category = category,
                amount = amount,
                mileage = mileage,
                comment = comment
            )
            expenseDao.insertExpense(expense)
        }
    }
}