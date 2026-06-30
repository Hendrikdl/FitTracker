package com.example.fittracker.data.model

data class UserProfile(
    val uid: String = "",

    // Personal
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val age: Int = 0,
    val sex: String = "",

    // Body stats
    val weightKg: Double = 0.0,
    val heightCm: Double = 0.0,

    // Measurements
    val chestCm: Double = 0.0,
    val waistCm: Double = 0.0,
    val hipsCm: Double = 0.0,
    val bicepsCm: Double = 0.0,
    val thighsCm: Double = 0.0,

    // Profile picture
    val profileImageUrl: String = ""
)