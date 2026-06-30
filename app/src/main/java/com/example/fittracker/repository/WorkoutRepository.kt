package com.example.fittracker.repository

import com.example.fittracker.data.local.dao.WorkoutExerciseDao
import com.example.fittracker.data.local.dao.WorkoutPlanDao
import com.example.fittracker.data.local.dao.WorkoutPlanScheduleDao
import com.example.fittracker.data.local.dao.WorkoutSessionDao
import com.example.fittracker.data.local.dao.WorkoutSessionExerciseDao
import com.example.fittracker.data.local.dao.WorkoutSetDao
import com.example.fittracker.data.local.entity.WorkoutPlan
import com.example.fittracker.data.local.entity.WorkoutPlanSchedule
import kotlinx.coroutines.flow.Flow
import com.example.fittracker.data.local.entity.WorkoutExercise
import com.example.fittracker.data.local.entity.WorkoutExerciseWithExercise
import com.example.fittracker.data.local.entity.WorkoutSession
import com.example.fittracker.data.local.entity.WorkoutSessionExercise
import com.example.fittracker.data.local.entity.WorkoutSet
import kotlinx.coroutines.flow.flatMapLatest
import com.example.fittracker.data.model.WorkoutPlanSummary
import kotlinx.coroutines.flow.map


class WorkoutRepository(

    private val planDao: WorkoutPlanDao,
    private val scheduleDao: WorkoutPlanScheduleDao,
    private val workoutExerciseDao: WorkoutExerciseDao,
    private val sessionDao: WorkoutSessionDao,
    private val sessionExerciseDao: WorkoutSessionExerciseDao,
    private val setDao: WorkoutSetDao
) {

    // -------------------------
    // PLANS
    // -------------------------
    suspend fun addExerciseToPlan(
        planId: Int,
        exerciseId: Int,
        sets: Int,
        reps: Int,
        weight: Float,
        rest: Int,
        order: Int
    ) {
        workoutExerciseDao.insert(
            WorkoutExercise(
                planId = planId,
                exerciseId = exerciseId,
                targetSets = sets,
                targetReps = reps,
                targetWeight = weight,
                restSeconds = rest,
                orderIndex = order
            )
        )
    }

    fun getExercisesWithDetails(planId: Int) =
        workoutExerciseDao.getExercisesWithDetails(planId)

    fun getSessionExercises(sessionId: Int) =
        sessionExerciseDao.getBySession(sessionId)

    fun getExercisesForPlan(planId: Int) =
        workoutExerciseDao.getExercisesForPlan(planId)

    suspend fun updateWorkoutExercise(
        planId: Int,
        exerciseId: Int,
        sets: Int,
        reps: Int,
        weight: Float
    ) {
        workoutExerciseDao.updateExercise(planId, exerciseId, sets, reps, weight)
    }

    suspend fun insertSets(sets: List<WorkoutSet>) {
        setDao.insertAll(sets)
    }

    suspend fun startWorkout(
        planId: Int,
        exercises: List<WorkoutExerciseWithExercise>
    ): Long {

        val sessionId = sessionDao.insert(
            WorkoutSession(planId = planId)
        )

        val sessionExercises = exercises.mapIndexed { index, item ->

            val ex = item.workoutExercise
            val details = item.exercise

            WorkoutSessionExercise(
                sessionId = sessionId.toInt(),
                exerciseId = ex.exerciseId,
                exerciseName = details.name,
                targetSets = ex.targetSets,
                targetReps = ex.targetReps,
                targetWeight = ex.targetWeight,
                orderIndex = index
            )
        }

        sessionExerciseDao.insertAll(sessionExercises)

        return sessionId
    }
    suspend fun removeExerciseFromPlan(planId: Int, exerciseId: Int) {
        workoutExerciseDao.deleteByPlanAndExercise(planId, exerciseId)
    }

    fun getPlans(): Flow<List<WorkoutPlan>> =
        planDao.getAllWorkoutPlans()

    fun getExerciseCount(planId: Int): Flow<Int> {
        return workoutExerciseDao.getExercisesForPlan(planId)
            .map { it.size }
    }

    fun getWorkoutPlanSummaries(): Flow<List<WorkoutPlanSummary>> {

        return planDao.getAllWorkoutPlans()
            .map { plans ->

                plans.map { plan ->

                    WorkoutPlanSummary(
                        plan = plan,
                        exerciseCount = workoutExerciseDao.getExerciseCount(plan.id),
                        scheduledDays = scheduleDao.getScheduledDays(plan.id)
                    )
                }

            }
    }

    suspend fun getPlanById(id: Int): WorkoutPlan? =
        planDao.getWorkoutPlanById(id)



    suspend fun createPlan(plan: WorkoutPlan): Long =
        planDao.insertWorkoutPlan(plan)

    suspend fun deletePlan(planId: Int) {
        planDao.deleteWorkoutPlan(planId)
        scheduleDao.deleteForPlan(planId)
    }


    // -------------------------
    // SCHEDULE
    // -------------------------



    fun getSchedule(planId: Int): Flow<List<WorkoutPlanSchedule>> =
        scheduleDao.getScheduleForPlan(planId)

    // -------------------------
    // TODAY'S WORKOUTS (IMPORTANT)
    // -------------------------

    suspend fun setSchedule(
        planId: Int,
        days: List<Int>
    ) {

        scheduleDao.deleteForPlan(planId)

        scheduleDao.insertAll(
            days.map {
                WorkoutPlanSchedule(
                    planId = planId,
                    dayOfWeek = it
                )
            }
        )
    }

    fun getPlansForDay(dayOfWeek: Int): Flow<List<WorkoutPlan>> {
        return scheduleDao.getPlansForDay(dayOfWeek)
            .flatMapLatest { schedules ->

                val planIds = schedules.map { it.planId }

                planDao.getPlansByIds(planIds)
            }
    }


}