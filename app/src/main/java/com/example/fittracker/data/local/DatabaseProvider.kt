package com.example.fittracker.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseProvider {

    @Volatile
    private var INSTANCE: FitTrackerDatabase? = null

    fun getDatabase(context: Context): FitTrackerDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                FitTrackerDatabase::class.java,
                "fittracker_db"
            ).addMigrations(MIGRATION_9_10)
                .fallbackToDestructiveMigration()
                .build()


            INSTANCE = instance
            instance
        }
    }

    val MIGRATION_9_10 = object : Migration(9, 10) {
        override fun migrate(database: SupportSQLiteDatabase) {

            database.execSQL("""
            CREATE TABLE workout_exercises_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                planId INTEGER NOT NULL,
                exerciseId INTEGER NOT NULL,
                orderIndex INTEGER NOT NULL
            )
        """)

            database.execSQL("""
            INSERT INTO workout_exercises_new (id, planId, exerciseId, orderIndex)
            SELECT id, planId, 0, orderIndex
            FROM workout_exercises
        """)

            database.execSQL("DROP TABLE workout_exercises")

            database.execSQL("ALTER TABLE workout_exercises_new RENAME TO workout_exercises")
        }
    }
}