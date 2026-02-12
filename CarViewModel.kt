package com.example.carcosts.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carcosts.data.local.dao.CarDao
import com.example.carcosts.data.local.entity.CarEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val carDao: CarDao
) : ViewModel() {

    val cars: StateFlow<List<CarEntity>> = carDao.getCars()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addCar(brand: String, model: String, year: String, mileage: String) {
        viewModelScope.launch {
            val car = CarEntity(
                brand = brand,
                model = model,
                year = year.toIntOrNull() ?: 2024,
                initialMileage = mileage.toIntOrNull() ?: 0
            )
            carDao.insertCar(car)
        }
    }
}