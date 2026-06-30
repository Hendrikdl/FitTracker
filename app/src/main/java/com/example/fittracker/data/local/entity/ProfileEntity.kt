package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(

    @PrimaryKey
    val id: Int = 1,

    val photoUri: String = "",

    val name: String,
    val surname: String,
    val email: String,

    val age: Int,
    val sex: String,

    val weightKg: Double,
    val targetWeightKg: Double = 0.0,
    val heightCm: Double,

    val chestCm: Double,
    val waistCm: Double,
    val hipsCm: Double,
    val bicepsCm: Double,
    val thighsCm: Double
)