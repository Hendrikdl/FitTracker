package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_plans")
data class WorkoutPlan(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val description: String = "",

    val createdDate: Long = System.currentTimeMillis()
)