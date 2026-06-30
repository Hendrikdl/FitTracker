package com.example.fittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fittracker.data.local.entity.WeightLog
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightLogDao {

    @Insert
    suspend fun insert(log: WeightLog)

    @Query("""
        SELECT *
        FROM weight_log
        ORDER BY date DESC
    """)
    fun getAll(): Flow<List<WeightLog>>

    @Query("""
        SELECT *
        FROM weight_log
        ORDER BY date DESC
        LIMIT 1
    """)
    suspend fun getLatest(): WeightLog?
}