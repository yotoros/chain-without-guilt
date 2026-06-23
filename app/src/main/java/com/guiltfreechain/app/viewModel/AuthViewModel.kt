package com.guiltfreechain.app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiltfreechain.app.data.repository.HabitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: HabitRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            repository.registerUser(name, email, password).fold(
                onSuccess = { user ->
                    _authState.value = AuthState.Success(user.id)
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Ошибка регистрации")
                }
            )
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            repository.loginUser(email, password).fold(
                onSuccess = { user ->
                    _authState.value = AuthState.Success(user.id)
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Ошибка входа")
                }
            )
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val userId: Int) : AuthState()
    data class Error(val message: String) : AuthState()
}