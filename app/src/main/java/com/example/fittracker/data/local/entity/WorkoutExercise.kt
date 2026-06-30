package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_exercises")
data class WorkoutExercise(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val planId: Int,

    val exerciseId: Int,

    val targetSets: Int = 3,

    val targetReps: Int = 10,

    val targetWeight: Float = 0f,

    val restSeconds: Int = 90,

    val orderIndex: Int
)