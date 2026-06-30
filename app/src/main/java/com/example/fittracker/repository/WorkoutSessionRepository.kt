package com.example.fittracker.repository

import com.example.fittracker.data.local.dao.ExerciseDao
import com.example.fittracker.data.local.dao.WorkoutPlanDao
import com.example.fittracker.data.local.dao.WorkoutSessionDao
import com.example.fittracker.data.local.dao.WorkoutSessionExerciseDao
import com.example.fittracker.data.local.dao.WorkoutSessionSetDao
import com.example.fittracker.data.local.entity.WorkoutExercise
import com.example.fittracker.data.local.entity.WorkoutSession
import com.example.fittracker.data.local.entity.WorkoutSessionExercise
import com.example.fittracker.data.local.entity.WorkoutSessionSet

class WorkoutSessionRepository(
    private val sessionDao: WorkoutSessionDao,
    private val setDao: WorkoutSessionSetDao,
    private val sessionExerciseDao: WorkoutSessionExerciseDao
) {

    suspend fun startWorkout(
        planId: Int,
        planExercises: List<WorkoutExercise>
    ): Long {

        val sessionId = sessionDao.startSession(
            WorkoutSession(planId = planId)
        )

        val sessionExercises = planExercises.map { ex ->

            WorkoutSessionExercise(
                sessionId = sessionId.toInt(),
                exerciseId = ex.exerciseId,

                // Temporary until we join with ExerciseEntity
                exerciseName = "",

                targetSets = ex.targetSets,
                targetReps = ex.targetReps,
                targetWeight = ex.targetWeight,

                orderIndex = ex.orderIndex
            )
        }

        sessionExerciseDao.insertAll(sessionExercises)

        return sessionId
    }

    suspend fun saveSet(
        sessionId: Int,
        exerciseId: Int,
        setNumber: Int,
        reps: Int,
        weight: Float
    ) {
        setDao.insertSet(
            WorkoutSessionSet(
                sessionId = sessionId,
                exerciseId = exerciseId,
                setNumber = setNumber,
                reps = reps,
                weight = weight,
                isCompleted = true
            )
        )
    }

    fun getSessionExercises(sessionId: Int) =
        sessionExerciseDao.getBySession(sessionId)

    fun getSessionSets(sessionId: Int) =
        setDao.getSets(sessionId)

    suspend fun completeSession(sessionId: Int) {
        sessionDao.completeSession(sessionId, System.currentTimeMillis())
    }
}