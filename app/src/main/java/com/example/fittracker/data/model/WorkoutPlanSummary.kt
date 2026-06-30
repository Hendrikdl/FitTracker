package com.example.fittracker.data.model

import com.example.fittracker.data.local.entity.WorkoutPlan

data class WorkoutPlanSummary(
    val plan: WorkoutPlan,
    val exerciseCount: Int,
    val scheduledDays: List<Int>
)