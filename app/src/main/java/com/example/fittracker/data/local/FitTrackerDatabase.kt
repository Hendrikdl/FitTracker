package com.example.fittracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fittracker.data.local.dao.*
import com.example.fittracker.data.local.entity.*
@Database(
    entities = [
        WorkoutPlan::class,
        WorkoutSession::class,
        WorkoutSet::class,
        PlannedSet::class,
        WeightLog::class,
        WeightGoal::class,
        ProfileEntity::class,
        PlanExerciseCrossRef::class,
        ExerciseEntity::class,
        WorkoutPlanSchedule::class,
        WorkoutExercise::class,
        WorkoutSessionExercise::class,
        WorkoutSessionSet::class,
    ],
    version = 17,
    exportSchema = false
)
abstract class FitTrackerDatabase : RoomDatabase() {


    abstract fun workoutSessionSetDao(): WorkoutSessionSetDao

    abstract fun profileDao(): ProfileDao
    abstract fun workoutExerciseDao(): WorkoutExerciseDao
    abstract fun workoutSessionExerciseDao(): WorkoutSessionExerciseDao

    abstract fun workoutPlanDao(): WorkoutPlanDao
    //abstract fun workoutExerciseDao(): WorkoutExerciseDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun workoutSetDao(): WorkoutSetDao
    abstract fun plannedSetDao(): PlannedSetDao
    abstract fun weightLogDao(): WeightLogDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun weightGoalDao(): WeightGoalDao
    abstract fun workoutPlanScheduleDao(): WorkoutPlanScheduleDao
}