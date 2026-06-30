package com.example.fittracker.data.local.entity

data class WorkoutExerciseWithDetails(
    val id: Int,
    val planId: Int,
    val exerciseId: Int,
    val name: String,
    val primaryMuscle: String?,
    val equipment: String?
)