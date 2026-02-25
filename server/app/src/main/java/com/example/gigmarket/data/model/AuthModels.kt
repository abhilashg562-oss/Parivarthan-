package com.example.gigmarket.data.model

data class LoginRequest(
    val phone: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val token: String,
    val role: String
)

data class RegisterResponse(
    val success: Boolean,
    val message: String
)

data class RegisterRequest(
    val name: String,
    val phone: String,
    val password: String,
    val role: String = "customer"
)

data class AuthResponse(
    val token: String,
    val user: UserData
)

data class UserData(
    val id: String,
    val name: String,
    val phone: String,
    val role: String
)
