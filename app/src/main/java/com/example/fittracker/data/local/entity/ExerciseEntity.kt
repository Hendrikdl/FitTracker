package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_library")
data class ExerciseEntity(
    @PrimaryKey
    val id: Int,

    val name: String,
    val description: String? = null,
    val instructions: String? = null,
    val primaryMuscle: String,
    val secondaryMuscles: String = "",
    val equipment: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean = false,
    val isCustom: Boolean = false
)
