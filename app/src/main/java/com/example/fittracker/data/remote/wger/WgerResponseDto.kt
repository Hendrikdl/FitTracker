package com.example.fittracker.data.remote.wger

data class WgerResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<WgerExerciseDto>
)