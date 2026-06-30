package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_schedule",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutPlan::class,
            parentColumns = ["id"],
            childColumns = ["planId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("planId")]
)
data class WorkoutPlanSchedule(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val planId: Int,

    // 1 = Monday ... 7 = Sunday
    val dayOfWeek: Int
)