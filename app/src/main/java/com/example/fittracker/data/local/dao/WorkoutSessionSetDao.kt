package com.example.fittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fittracker.data.local.entity.WorkoutSessionSet
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionSetDao {

    @Insert
    suspend fun insertSet(set: WorkoutSessionSet)

    @Query("SELECT * FROM workout_session_sets WHERE sessionId = :sessionId")
    fun getSets(sessionId: Int): Flow<List<WorkoutSessionSet>>

    @Query("""
        UPDATE workout_session_sets 
        SET reps = :reps, weight = :weight, isCompleted = :done
        WHERE id = :setId
    """)
    suspend fun updateSet(setId: Int, reps: Int, weight: Float, done: Boolean)
}