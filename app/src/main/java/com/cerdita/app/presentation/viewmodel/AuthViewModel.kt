package com.cerdita.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerdita.app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
    val uiState: StateFlow<AuthUiState> = _uiState

    init {
        checkExistingSession()
    }

    private fun checkExistingSession() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepository.loginWithToken()
                .onSuccess { _uiState.value = AuthUiState.LoggedIn }
                .onFailure { _uiState.value = AuthUiState.LoggedOut }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepository.register(username, password)
                .onSuccess { _uiState.value = AuthUiState.LoggedIn }
                .onFailure { e -> _uiState.value = AuthUiState.Error(e.message ?: "Error") }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepository.login(username, password)
                .onSuccess { _uiState.value = AuthUiState.LoggedIn }
                .onFailure { e -> _uiState.value = AuthUiState.Error(e.message ?: "Error") }
        }
    }

    fun logout() {
        authRepository.logout()
        _uiState.value = AuthUiState.LoggedOut
    }
}

sealed class AuthUiState {
    object Initial : AuthUiState()
    object Loading : AuthUiState()
    object LoggedIn : AuthUiState()
    object LoggedOut : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
