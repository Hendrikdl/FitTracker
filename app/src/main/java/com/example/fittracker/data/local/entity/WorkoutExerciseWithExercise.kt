package com.example.fittracker.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutExerciseWithExercise(

    @Embedded
    val workoutExercise: WorkoutExercise,

    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "id"
    )
    val exercise: ExerciseEntity
)