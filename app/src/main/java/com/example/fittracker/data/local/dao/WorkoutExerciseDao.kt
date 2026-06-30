package com.example.fittracker.data.local.dao

import androidx.room.*
import com.example.fittracker.data.local.entity.WorkoutExercise
import com.example.fittracker.data.local.entity.WorkoutExerciseWithExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: WorkoutExercise)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<WorkoutExercise>)

    @Delete
    suspend fun delete(exercise: WorkoutExercise)

    @Transaction
    @Query("SELECT * FROM workout_exercises WHERE planId = :planId ORDER BY orderIndex")
    fun getExercisesWithDetails(
        planId: Int
    ): Flow<List<WorkoutExerciseWithExercise>>

    @Query("DELETE FROM workout_exercises WHERE planId = :planId")
    suspend fun deleteByPlan(planId: Int)

    @Query("""
    DELETE FROM workout_exercises
    WHERE planId = :planId AND exerciseId = :exerciseId
""")
    suspend fun deleteExercise(planId: Int, exerciseId: Int)

    @Query("SELECT * FROM workout_exercises WHERE planId = :planId ORDER BY orderIndex ASC")
    fun getExercisesForPlan(planId: Int): Flow<List<WorkoutExercise>>

    @Query("""
    UPDATE workout_exercises
    SET targetSets = :sets,
        targetReps = :reps,
        targetWeight = :weight
    WHERE planId = :planId AND exerciseId = :exerciseId
""")
    suspend fun updateExercise(
        planId: Int,
        exerciseId: Int,
        sets: Int,
        reps: Int,
        weight: Float
    )

    @Query("""
    DELETE FROM workout_exercises
    WHERE planId = :planId
    AND exerciseId = :exerciseId
""")
    suspend fun deleteByPlanAndExercise(planId: Int, exerciseId: Int)


    @Query("SELECT * FROM workout_exercises")
    fun getAll(): Flow<List<WorkoutExercise>>

    @Query("""
    SELECT COUNT(*)
    FROM workout_exercises
    WHERE planId = :planId
""")
    suspend fun getExerciseCount(planId: Int): Int
}