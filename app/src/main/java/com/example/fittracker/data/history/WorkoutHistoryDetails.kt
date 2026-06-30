package com.example.fittracker.data.history

import com.example.fittracker.data.local.entity.WorkoutSession
import com.example.fittracker.data.local.entity.WorkoutSet

data class WorkoutHistoryDetails(
    val session: WorkoutSession,
    val planName: String,
    val exercises: List<ExerciseHistory>,
    val summary: WorkoutSummary
)

data class ExerciseHistory(

    val exerciseName: String,

    val sets: List<WorkoutSet>,

    val totalVolume: Double,

    val maxWeight: Double
)

data class WorkoutSummary(

    val durationMinutes: Int,

    val totalExercises: Int,

    val totalSets: Int,

    val totalReps: Int,

    val totalVolume: Double,

    val heaviestWeight: Double
)