package com.example.gigmarket.network

data class LoginRequest(
    val phone: String,
    val password: String
)

// 🔹 Response received from backend
data class LoginResponse(
    val success: Boolean,
    val token: String
)

data class RegisterRequest(
    val name: String,
    val phone: String,
    val password: String,
    val role: String = "customer" // or "provider"
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