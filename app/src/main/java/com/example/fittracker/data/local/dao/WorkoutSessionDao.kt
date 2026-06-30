package com.example.fittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fittracker.data.local.entity.WorkoutSession
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionDao {

    @Insert
    suspend fun startSession(session: WorkoutSession): Long


    @Query("SELECT * FROM workout_sessions WHERE status = 'ACTIVE'")
    fun getActiveSession(): Flow<WorkoutSession?>

    @Insert
    suspend fun insert(session: WorkoutSession): Long

    @Query("SELECT * FROM workout_sessions WHERE id = :id")
    suspend fun getSession(id: Int): WorkoutSession?

    @Query("UPDATE workout_sessions SET endTime = :endTime, status = 'COMPLETED' WHERE id = :sessionId")
    suspend fun completeSession(sessionId: Int, endTime: Long)
}