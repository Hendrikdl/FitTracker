package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_session_sets")
data class WorkoutSessionSet(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val sessionId: Int,

    val exerciseId: Int,

    val setNumber: Int,

    val reps: Int,

    val weight: Float,

    val isCompleted: Boolean = false
)