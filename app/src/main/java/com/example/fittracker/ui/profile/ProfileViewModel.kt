package com.example.fittracker.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittracker.data.local.entity.ProfileEntity
import com.example.fittracker.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadProfile()
    }

    fun syncProfile() {
        viewModelScope.launch {
            try {
                repository.syncFromFirestore()
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Sync failed", e)
            }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val profile = repository.getLocalProfile()

            android.util.Log.d(
                "ProfileViewModel",
                "Loaded profile = $profile"
            )

            if (profile != null) {
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    user = profile
                )
            } else {
                _uiState.value = ProfileUiState(isLoading = false)
            }
        }
    }

    fun saveProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            repository.saveProfile(profile)
            loadProfile()
        }
    }
}