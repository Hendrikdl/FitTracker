package com.example.fittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fittracker.data.local.entity.WorkoutSessionExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionExerciseDao {

    @Insert
    suspend fun insertAll(items: List<WorkoutSessionExercise>)

    @Query("""
    DELETE FROM workout_session_exercises 
    WHERE sessionId = :sessionId
""")
    suspend fun deleteBySession(sessionId: Int)

    @Insert
    suspend fun insert(item: WorkoutSessionExercise)

    @Query("""
    SELECT * 
    FROM workout_session_exercises
    WHERE exerciseId = :exerciseId
""")
    fun getByExercise(exerciseId: Int): Flow<List<WorkoutSessionExercise>>

    @Query("""
        SELECT * 
        FROM workout_session_exercises 
        WHERE sessionId = :sessionId 
        ORDER BY orderIndex
    """)
    fun getBySession(sessionId: Int): Flow<List<WorkoutSessionExercise>>
}