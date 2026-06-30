package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_log")
data class WeightLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val weight: Double,
    val date: Long
)