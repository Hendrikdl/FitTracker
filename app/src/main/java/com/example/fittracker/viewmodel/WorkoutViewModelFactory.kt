package com.example.fittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fittracker.data.repository.ExerciseRepository
import com.example.fittracker.repository.WorkoutRepository
import com.example.fittracker.repository.WorkoutSessionRepository

class WorkoutViewModelFactory(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    private val sessionRepository: WorkoutSessionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            return WorkoutViewModel(
                workoutRepository,
                exerciseRepository,
                sessionRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}