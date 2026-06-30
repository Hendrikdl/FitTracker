package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlannedSet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val exerciseId: Int,

    val setNumber: Int,

    val targetWeight: Double,
    val targetReps: Int
)