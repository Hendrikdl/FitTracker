package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_sets")
data class WorkoutSet(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val sessionExerciseId: Int,

    val setNumber: Int,

    val reps: Int,

    val weight: Float,

    val isCompleted: Boolean = false
)