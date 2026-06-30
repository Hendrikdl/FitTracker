package com.example.fittracker.ui.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted

import kotlinx.coroutines.launch

import com.example.fittracker.data.repository.AnalyticsRepository

class AnalyticsViewModel(
    private val repo: AnalyticsRepository
) : ViewModel() {

    val weightHistory = repo.getWeightHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val goal = repo.getGoal()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun addWeight(weight: Double) {
        viewModelScope.launch {
            repo.addWeight(weight)
        }
    }

    fun setGoal(weight: Double) {
        viewModelScope.launch {
            repo.setGoal(weight)
        }
    }
}