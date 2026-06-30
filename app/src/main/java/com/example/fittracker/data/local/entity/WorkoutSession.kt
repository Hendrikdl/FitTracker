package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_sessions")
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val planId: Int,

    val startTime: Long = System.currentTimeMillis(),

    val endTime: Long? = null,

    val status: String = "ACTIVE" // ACTIVE, COMPLETED
)