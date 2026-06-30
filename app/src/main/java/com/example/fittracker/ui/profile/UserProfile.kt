package com.example.fittracker.ui.profile

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val age: Int = 0,
    val weightKg: Double = 0.0,
    val heightCm: Double = 0.0,
    val sex: String = "",
    val profileImageUrl: String = ""
)