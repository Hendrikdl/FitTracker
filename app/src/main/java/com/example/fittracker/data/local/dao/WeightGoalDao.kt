package com.example.fittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fittracker.data.local.entity.WeightGoal
import kotlinx.coroutines.flow.Flow
@Dao
interface WeightGoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setGoal(goal: WeightGoal)

    @Query("SELECT * FROM weight_goal WHERE id = 1")
    fun getGoal(): Flow<WeightGoal?>
}