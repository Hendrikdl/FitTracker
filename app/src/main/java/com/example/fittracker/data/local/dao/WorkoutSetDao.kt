package com.example.fittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fittracker.data.local.entity.WorkoutSet
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSetDao {

    @Insert
    suspend fun insert(set: WorkoutSet)

    @Insert
    suspend fun insertAll(sets: List<WorkoutSet>)

    @Query("SELECT * FROM workout_sets WHERE sessionExerciseId = :id ORDER BY setNumber")
    fun getByExercise(id: Int): Flow<List<WorkoutSet>>

    @Update
    suspend fun update(set: WorkoutSet)
}