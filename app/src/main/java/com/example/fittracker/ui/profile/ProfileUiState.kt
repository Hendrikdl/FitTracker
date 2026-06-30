package com.example.fittracker.ui.profile

import com.example.fittracker.data.local.entity.ProfileEntity

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: ProfileEntity? = null,
    val error: String? = null
)