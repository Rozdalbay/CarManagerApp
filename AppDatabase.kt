package com.example.carcosts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.carcosts.data.local.dao.CarDao
import com.example.carcosts.data.local.dao.ExpenseDao
import com.example.carcosts.data.local.entity.CarEntity
import com.example.carcosts.data.local.entity.ExpenseEntity

@Database(entities = [CarEntity::class, ExpenseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun expenseDao(): ExpenseDao
}