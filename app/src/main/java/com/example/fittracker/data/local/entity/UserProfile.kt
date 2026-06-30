package com.example.fittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserProfile(
    @PrimaryKey val uid: String,
    val name: String,
    val height: Double?,
    val weight: Double?
)