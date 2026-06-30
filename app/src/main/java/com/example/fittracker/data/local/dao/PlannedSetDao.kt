package com.example.fittracker.data.local.dao

import androidx.room.*
import com.example.fittracker.data.local.entity.PlannedSet
import kotlinx.coroutines.flow.Flow

@Dao
interface PlannedSetDao {

    @Query("SELECT * FROM PlannedSet WHERE exerciseId = :exerciseId ORDER BY setNumber ASC")
    fun getPlannedSetsForExercise(exerciseId: Int): Flow<List<PlannedSet>>


    @Query("DELETE FROM PlannedSet WHERE id = :id")
    suspend fun delete(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plannedSet: PlannedSet)

    @Query("SELECT * FROM PlannedSet WHERE exerciseId = :exerciseId ORDER BY setNumber ASC")
    fun getSetsForExercise(exerciseId: Int): Flow<List<PlannedSet>>

    @Query("DELETE FROM PlannedSet WHERE exerciseId = :exerciseId")
    suspend fun deleteForExercise(exerciseId: Int)
}