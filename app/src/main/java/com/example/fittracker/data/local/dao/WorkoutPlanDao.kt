package com.example.fittracker.data.local.dao

import androidx.room.*
import com.example.fittracker.data.local.entity.WorkoutPlan
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutPlan(plan: WorkoutPlan): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutPlans(plans: List<WorkoutPlan>)

    @Update
    suspend fun updateWorkoutPlan(plan: WorkoutPlan)

    @Query("""
    DELETE FROM workout_exercises
    WHERE planId = :planId
    AND exerciseId = :exerciseId
""")
    suspend fun deleteByPlanAndExercise(planId: Int, exerciseId: Int)

    @Query("""
    SELECT COUNT(*)
    FROM workout_exercises
    WHERE planId = :planId
""")
    suspend fun getExerciseCount(planId: Int): Int

    @Query("""
        DELETE FROM workout_plans
        WHERE id = :id
    """)
    suspend fun deleteWorkoutPlan(id: Int)

    @Query("""
    SELECT *
    FROM workout_plans
    WHERE id IN (:ids)
    ORDER BY createdDate DESC
""")
    fun getPlansByIds(ids: List<Int>): Flow<List<WorkoutPlan>>

    @Query("""
        SELECT *
        FROM workout_plans
        ORDER BY createdDate DESC
    """)
    fun getAllWorkoutPlans(): Flow<List<WorkoutPlan>>

    @Query("""
        SELECT *
        FROM workout_plans
        WHERE id = :id
        LIMIT 1
    """)
    suspend fun getWorkoutPlanById(id: Int): WorkoutPlan?
}