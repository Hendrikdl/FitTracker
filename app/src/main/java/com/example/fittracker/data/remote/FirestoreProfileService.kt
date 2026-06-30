package com.example.fittracker.data.remote

import android.util.Log
import com.example.fittracker.data.local.entity.ProfileEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreProfileService {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun userId(): String {
        return auth.currentUser?.uid
            ?: throw Exception("User not logged in")
    }

    suspend fun getProfile(): ProfileEntity? {
        return try {

            val snapshot = db
                .collection("users")
                .document(userId())
                .collection("profile")
                .document("main")
                .get()
                .await()

            snapshot.toObject(ProfileEntity::class.java)

        } catch (e: Exception) {

            Log.e(
                "FirestoreProfileService",
                "Failed to load profile",
                e
            )

            null
        }
    }

    suspend fun saveProfile(profile: ProfileEntity): Boolean {
        return try {

            db
                .collection("users")
                .document(userId())
                .collection("profile")
                .document("main")
                .set(profile)
                .await()

            Log.d("FIRESTORE", "SAVE SUCCESS")

            true

        } catch (e: Exception) {

            Log.e(
                "FirestoreProfileService",
                "Failed to save profile",
                e
            )

            false
        }
    }
}