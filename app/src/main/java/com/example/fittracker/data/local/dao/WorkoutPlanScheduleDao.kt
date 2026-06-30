package com.example.fittracker.data.local.dao

import androidx.room.*
import com.example.fittracker.data.local.entity.WorkoutPlanSchedule
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutPlanScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: WorkoutPlanSchedule): Long

    @Query("""
    SELECT dayOfWeek
    FROM workout_schedule
    WHERE planId = :planId
    ORDER BY dayOfWeek
""")
    suspend fun getScheduledDays(planId: Int): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(schedules: List<WorkoutPlanSchedule>)

    @Query("""
        SELECT *
        FROM workout_schedule
        WHERE planId = :planId
        ORDER BY dayOfWeek ASC
    """)
    fun getScheduleForPlan(planId: Int): Flow<List<WorkoutPlanSchedule>>

    @Query("""
        SELECT *
        FROM workout_schedule
        WHERE dayOfWeek = :day
    """)
    fun getPlansForDay(day: Int): Flow<List<WorkoutPlanSchedule>>

    @Query("""
        DELETE FROM workout_schedule
        WHERE planId = :planId
    """)
    suspend fun deleteForPlan(planId: Int)
}