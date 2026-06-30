package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_session_exercises")
data class WorkoutSessionExercise(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val sessionId: Int,

    val exerciseId: Int,

    val exerciseName: String,

    val targetSets: Int,

    val targetReps: Int,

    val targetWeight: Float,

    val orderIndex: Int
)