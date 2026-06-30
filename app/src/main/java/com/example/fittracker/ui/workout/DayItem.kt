package com.example.fittracker.ui.workout

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DayItem(
    val label: String,
    val value: Int,
    val selected: MutableState<Boolean> = mutableStateOf(false)
)