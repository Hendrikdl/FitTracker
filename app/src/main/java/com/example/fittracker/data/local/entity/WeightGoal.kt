package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_goal")
data class WeightGoal(
    @PrimaryKey val id: Int = 1,
    val targetWeight: Double
)