package com.example.carcosts.di

import android.content.Context
import androidx.room.Room
import com.example.carcosts.data.local.AppDatabase
import com.example.carcosts.data.local.dao.CarDao
import com.example.carcosts.data.local.dao.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "car_costs_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCarDao(db: AppDatabase): CarDao {
        return db.carDao()
    }

    @Provides
    @Singleton
    fun provideExpenseDao(db: AppDatabase): ExpenseDao {
        return db.expenseDao()
    }
}