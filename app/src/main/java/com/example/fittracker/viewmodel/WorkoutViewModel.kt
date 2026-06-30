package com.example.fittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittracker.data.local.entity.WorkoutPlan
import com.example.fittracker.data.local.entity.WorkoutPlanSchedule
import com.example.fittracker.repository.WorkoutRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import com.example.fittracker.data.local.entity.ExerciseEntity
import com.example.fittracker.data.local.entity.WorkoutSet
import com.example.fittracker.data.repository.ExerciseRepository
import com.example.fittracker.repository.WorkoutSessionRepository

class WorkoutViewModel(
    private val repo: WorkoutRepository,
    private val exerciseRepo: ExerciseRepository,
    private val sessionRepo: WorkoutSessionRepository,
    private val repository: WorkoutRepository
) : ViewModel() {

    // -------------------------
    // ALL PLANS
    // -------------------------

    fun searchExercises(query: String) =
        exerciseRepo.searchExercises(query)


    init {
        viewModelScope.launch {
            exerciseRepo.syncExercisesFromApi()
        }
    }

    val plans: StateFlow<List<WorkoutPlan>> =
        repo.getPlans()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    

    fun saveExerciseSets(sets: List<WorkoutSet>) {
        viewModelScope.launch {
            repository.insertSets(sets)
        }
    }

    fun addExerciseToPlan(
        planId: Int,
        exerciseId: Int,
        sets: Int,
        reps: Int,
        weight: Float,
        rest: Int,
        order: Int
    ) {
        viewModelScope.launch {
            repo.addExerciseToPlan(
                planId,
                exerciseId,
                sets,
                reps,
                weight,
                rest,
                order
            )
        }
    }

    fun getExercisesWithDetails(planId: Int) =
        repo.getExercisesWithDetails(planId)


    fun getExercisesForPlan(planId: Int) =
        repo.getExercisesForPlan(planId)

    val exercises: StateFlow<List<ExerciseEntity>> =
        exerciseRepo.getAllExercises()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    // -------------------------
    // SCHEDULE
    // -------------------------



    fun startWorkout(planId: Int, onStarted: (Long) -> Unit) {
        viewModelScope.launch {
            val planExercises = repo.getExercisesForPlan(planId).first()

            val sessionId = sessionRepo.startWorkout(
                planId,
                planExercises
            )

            onStarted(sessionId)
        }
    }
    fun getSessionExercises(sessionId: Int) =
        sessionRepo.getSessionExercises(sessionId)

    fun saveSet(
        sessionId: Int,
        exerciseId: Int,
        setNumber: Int,
        reps: Int,
        weight: Float
    ) {
        viewModelScope.launch {
            sessionRepo.saveSet(
                sessionId,
                exerciseId,
                setNumber,
                reps,
                weight
            )
        }
    }

    fun getSchedule(planId: Int): Flow<List<WorkoutPlanSchedule>> =
        repo.getSchedule(planId)

    // -------------------------
    // CREATE PLAN
    // -------------------------

    fun createPlan(
        name: String,
        description: String = "",
        daysOfWeek: List<Int>
    ) {
        viewModelScope.launch {

            val planId = repo.createPlan(
                WorkoutPlan(
                    name = name,
                    description = description
                )
            )

            repo.setSchedule(planId.toInt(), daysOfWeek)
        }
    }

    // -------------------------
    // DELETE PLAN
    // -------------------------

    fun deletePlan(planId: Int) {
        viewModelScope.launch {
            repo.deletePlan(planId)
        }
    }

    fun setSchedule(planId: Int, daysOfWeek: List<Int>) {
        viewModelScope.launch {
            repo.setSchedule(planId, daysOfWeek)
        }
    }

    fun removeExerciseFromPlan(planId: Int, exerciseId: Int) {
        viewModelScope.launch {
            repo.removeExerciseFromPlan(planId, exerciseId)
        }
    }

    fun updateExerciseInPlan(
        planId: Int,
        exerciseId: Int,
        sets: Int,
        reps: Int,
        weight: Float
    ) {
        viewModelScope.launch {
            repo.updateWorkoutExercise(
                planId,
                exerciseId,
                sets,
                reps,
                weight
            )
        }
    }

    fun startTodayWorkout(onStarted: (Long) -> Unit) {
        viewModelScope.launch {

            val today = getTodayDayOfWeek()

            val todayPlans = repo.getPlansForDay(today).first()

            if (todayPlans.isEmpty()) return@launch

            val plan = todayPlans.first() // (Phase 4.4.2 we will support multiple)

            val planExercises = repo.getExercisesForPlan(plan.id).first()

            val sessionId = sessionRepo.startWorkout(
                plan.id,
                planExercises
            )

            onStarted(sessionId)
        }
    }



    fun startWorkoutSafe(planId: Int, onStarted: (Long) -> Unit) {
        viewModelScope.launch {

            val exercises = repo.getExercisesForPlan(planId).first()

            if (exercises.isEmpty()) {
                // You can later replace with UI state / toast
                return@launch
            }

            val sessionId = sessionRepo.startWorkout(
                planId,
                exercises
            )

            onStarted(sessionId)
        }
    }

    // -------------------------
    // TODAY'S PLANS (FIXED - NO FLOW CHAOS)
    // -------------------------

    fun getTodayPlans(): Flow<List<WorkoutPlan>> {

        val today = getTodayDayOfWeek()

        return repo.getPlansForDay(today)
    }
    // -------------------------
    // HELPERS
    // -------------------------

    private fun getTodayDayOfWeek(): Int {
        return when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            Calendar.SUNDAY -> 7
            else -> 1
        }
    }
}