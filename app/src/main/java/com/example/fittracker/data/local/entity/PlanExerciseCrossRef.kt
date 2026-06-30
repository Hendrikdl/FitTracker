package com.example.fittracker.data.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["planId", "exerciseId"])
data class PlanExerciseCrossRef(
    val planId: Int,
    val exerciseId: Int
)