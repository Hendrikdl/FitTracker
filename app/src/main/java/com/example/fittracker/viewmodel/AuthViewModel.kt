package com.example.fittracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fittracker.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {

    private val repo = AuthRepository()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        _loading.value = true
        repo.login(email, password) { success, message ->
            _loading.value = false
            if (success) {
                onSuccess()
            } else {
                _error.value = message
            }
        }
    }

    fun register(email: String, password: String, onSuccess: () -> Unit) {
        _loading.value = true
        repo.register(email, password) { success, message ->
            _loading.value = false
            if (success) {
                onSuccess()
            } else {
                _error.value = message
            }
        }
    }

    fun logout() {
        repo.logout()
    }

    fun isLoggedIn() = repo.currentUser() != null
}