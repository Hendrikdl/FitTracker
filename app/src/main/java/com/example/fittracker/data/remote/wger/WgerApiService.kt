package com.example.fittracker.data.remote.wger

import retrofit2.http.GET
import retrofit2.http.Query

interface WgerApiService {

    @GET("exerciseinfo/")
    suspend fun getExercises(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): WgerResponseDto
}