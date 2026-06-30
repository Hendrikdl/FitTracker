package com.example.fittracker.data.remote.wger

data class WgerResponse(
    val results: List<WgerExerciseDto>
)

data class WgerExerciseDto(

    val id: Int,

    val category: WgerCategoryDto?,

    val muscles: List<WgerMuscleDto>?,

    val muscles_secondary: List<WgerMuscleDto>?,

    val equipment: List<WgerEquipmentDto>?,

    val translations: List<WgerTranslationDto>?
)

data class WgerCategoryDto(
    val id: Int,
    val name: String
)

data class WgerMuscleDto(
    val id: Int,
    val name: String
)

data class WgerEquipmentDto(
    val id: Int,
    val name: String
)

data class WgerTranslationDto(
    val id: Int,
    val name: String?,
    val description: String?
)

