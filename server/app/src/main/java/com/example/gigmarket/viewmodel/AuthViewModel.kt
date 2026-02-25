package com.example.gigmarket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gigmarket.data.local.TokenManager
import com.example.gigmarket.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository()
    private val tokenManager = TokenManager(application)

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    init {
        checkToken()
    }

    private fun checkToken() {
        viewModelScope.launch {
            val token = tokenManager.getToken().first()
            val role = tokenManager.getRole().first()
            _isLoggedIn.value = !token.isNullOrEmpty()
            _userRole.value = role
        }
    }

    fun login(phone: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.login(phone, password)

            result.onSuccess { (token, role) ->
                tokenManager.saveAuthData(token, role)
                _userRole.value = role
                _isLoggedIn.value = true
                _authState.value = AuthState.Success("Login Success 🚀")
            }

            result.onFailure {
                _authState.value = AuthState.Error(it.message ?: "Login Failed ❌")
            }
        }
    }

    fun register(name: String, phone: String, password: String, role: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.register(name, phone, password, role)

            result.onSuccess { msg ->
                _authState.value = AuthState.Success(msg)
            }

            result.onFailure {
                _authState.value = AuthState.Error(it.message ?: "Registration Failed ❌")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenManager.clearAuthData()
            _isLoggedIn.value = false
            _userRole.value = null
        }
    }
}