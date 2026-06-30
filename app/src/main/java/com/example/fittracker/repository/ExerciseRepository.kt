package com.example.fittracker.data.repository

import android.util.Log
import com.example.fittracker.data.local.dao.ExerciseDao
import com.example.fittracker.data.local.entity.ExerciseEntity
import com.example.fittracker.data.remote.wger.WgerClient
import com.example.fittracker.data.remote.wger.WgerExerciseDto

class ExerciseRepository(
    private val dao: ExerciseDao
) {

    // ----------------------------
    // DTO → ENTITY MAPPING
    // ----------------------------
    fun mapDtoToEntity(dto: WgerExerciseDto): ExerciseEntity {

        val translation = dto.translations?.firstOrNull()

        return ExerciseEntity(
            id = dto.id,
            name = translation?.name ?: "Unnamed Exercise",
            description = translation?.description ?: "",
            instructions = "",
            primaryMuscle = dto.muscles?.firstOrNull()?.name ?: "Unknown",
            secondaryMuscles = dto.muscles_secondary?.joinToString(", ") { it.name } ?: "",
            equipment = dto.equipment?.joinToString(", ") { it.name } ?: "",
            category = dto.category?.name ?: "",
            imageUrl = "",
            isFavorite = false,
            isCustom = false
        )
    }

    // ----------------------------
    // SYNC
    // ----------------------------
    suspend fun syncExercisesFromApi() {

        try {
            Log.d("WGER_SYNC", "Starting sync...")

            var offset = 0
            val limit = 50

            while (true) {

                val response = WgerClient.api.getExercises(limit, offset)
                Log.d("WGER_SYNC", "Raw response page=$offset size=${response.results.size}")
                val results = response.results

                if (results.isEmpty()) break

                val mapped = results.map { mapDtoToEntity(it) }

                dao.insertAll(mapped)

                offset += limit

                if (results.size < limit) break
            }

            Log.d("WGER_SYNC", "Sync complete")

        } catch (e: Exception) {
            Log.e("WGER_SYNC", "Sync failed", e)
        }
    }

    // ----------------------------
    // CRUD
    // ----------------------------
    fun getAllExercises() = dao.getAllExercises()

    fun searchExercises(query: String) = dao.searchExercises(query)

    suspend fun insert(exercise: ExerciseEntity) = dao.insert(exercise)

    suspend fun insertAll(exercises: List<ExerciseEntity>) = dao.insertAll(exercises)

    suspend fun update(exercise: ExerciseEntity) = dao.update(exercise)

    suspend fun delete(exercise: ExerciseEntity) = dao.delete(exercise)

    suspend fun clear() = dao.clear()
}
