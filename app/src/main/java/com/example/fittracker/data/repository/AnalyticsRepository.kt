package com.example.fittracker.data.repository

import com.example.fittracker.data.local.entity.WeightGoal
import com.example.fittracker.data.local.dao.WeightGoalDao
import com.example.fittracker.data.local.dao.WeightLogDao
import com.example.fittracker.data.local.entity.WeightLog

class AnalyticsRepository(
    private val weightDao: WeightLogDao,
    private val goalDao: WeightGoalDao
) {

    fun getWeightHistory() = weightDao.getAll()

    fun getGoal() = goalDao.getGoal()

    suspend fun addWeight(weight: Double) {
        weightDao.insert(
            WeightLog(
                weight = weight,
                date = System.currentTimeMillis()
            )
        )
    }

    suspend fun setGoal(weight: Double) {
        goalDao.setGoal(
            WeightGoal(
                id = 1,
                targetWeight = weight
            )
        )
    }
}