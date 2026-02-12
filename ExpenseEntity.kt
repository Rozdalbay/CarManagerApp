package com.example.carcosts.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "expenses",
    foreignKeys = [ForeignKey(
        entity = CarEntity::class,
        parentColumns = ["id"],
        childColumns = ["carId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val carId: Int,
    val date: Long,
    val category: String,
    val amount: Double,
    val mileage: Int,
    val comment: String?
)