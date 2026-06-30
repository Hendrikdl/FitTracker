package com.example.fittracker.data.remote

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthManager {

    private val auth = FirebaseAuth.getInstance()

    fun currentUid(): String? {
        return auth.currentUser?.uid
    }

    fun currentEmail(): String? {
        return auth.currentUser?.email
    }
}