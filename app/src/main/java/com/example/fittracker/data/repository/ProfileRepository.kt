package com.example.fittracker.data.repository

import android.util.Log
import com.example.fittracker.data.local.dao.ProfileDao
import com.example.fittracker.data.local.entity.ProfileEntity
import com.example.fittracker.data.remote.FirestoreProfileService
import com.example.fittracker.data.local.dao.WeightLogDao
import com.example.fittracker.data.local.entity.WeightLog

class ProfileRepository(
    private val dao: ProfileDao,
    private val weightLogDao: WeightLogDao,
    private val firestore: FirestoreProfileService
) {

    // Save locally + cloud
    suspend fun saveProfile(profile: ProfileEntity) {

        Log.d(
            "ProfileRepository",
            "Saving profile locally"
        )

        dao.saveProfile(profile)

        // Save weight history only if the weight changed
        val latest = weightLogDao.getLatest()

        if (latest == null || latest.weight != profile.weightKg) {

            weightLogDao.insert(

                WeightLog(
                    weight = profile.weightKg,
                    date = System.currentTimeMillis()
                )
            )

            Log.d(
                "ProfileRepository",
                "Weight history updated: ${profile.weightKg}"
            )
        }

        Log.d(
            "ProfileRepository",
            "Local save successful"
        )

        try {

            Log.d(
                "ProfileRepository",
                "Saving profile to Firestore"
            )

            firestore.saveProfile(profile)

            Log.d(
                "ProfileRepository",
                "Firestore save successful"
            )

        } catch (e: Exception) {

            Log.e(
                "ProfileRepository",
                "Firestore save failed",
                e
            )
        }

    }

    // Load from local DB (fast)
    suspend fun getLocalProfile(): ProfileEntity? {
        return dao.getProfile()
    }

    // Sync from Firestore → Room
    suspend fun syncFromFirestore(): ProfileEntity? {
        val remote = firestore.getProfile()

        if (remote != null) {
            dao.upsertProfile(remote)
        }

        return remote
    }
}