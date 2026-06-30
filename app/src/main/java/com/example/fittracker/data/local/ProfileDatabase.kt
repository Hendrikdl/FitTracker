package com.example.fittracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fittracker.data.local.dao.ProfileDao
import com.example.fittracker.data.local.entity.ProfileEntity

@Database(
    entities = [ProfileEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ProfileDatabase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao
}