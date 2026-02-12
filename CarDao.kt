package com.example.carcosts.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.carcosts.data.local.entity.CarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM cars")
    fun getCars(): Flow<List<CarEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: CarEntity)

    @Delete
    suspend fun deleteCar(car: CarEntity)
}