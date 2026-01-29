package com.example.noteapp.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.AuthRepository
import com.example.noteapp.ui.ui_state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    init {
        _uiState.value = _uiState.value.copy(isLoggedIn = authRepository.getCurrentUser() != null)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                authRepository.login(email.trim(), password)
                _uiState.value = _uiState.value.copy(isLoading = false, isLoggedIn = true)
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(isLoading = false, error = e.message ?: "Login failed")
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                authRepository.register(email, password)
                _uiState.value = _uiState.value.copy(isLoading = false, isLoggedIn = true)
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(isLoading = false, error = e.message ?: "Register failed")
            }
        }
    }

    fun logout() {
        authRepository.signOut()
        _uiState.value = _uiState.value.copy(isLoggedIn = false)
    }
}

