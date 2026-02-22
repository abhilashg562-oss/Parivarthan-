package com.example.gigmarket.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gigmarket.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _loginState = MutableStateFlow("")
    val loginState: StateFlow<String> = _loginState

    fun login(phone: String, password: String) {

        viewModelScope.launch {

            val result = repository.login(phone, password)

            result.onSuccess { token ->
                _loginState.value = "Login Success 🚀"
                // Later we will store this token
            }

            result.onFailure {
                _loginState.value = it.message ?: "Login Failed ❌"
            }
        }
    }
}