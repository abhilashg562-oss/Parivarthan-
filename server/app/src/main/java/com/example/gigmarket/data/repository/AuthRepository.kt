package com.example.gigmarket.data.repository

import com.example.gigmarket.data.model.LoginRequest
import com.example.gigmarket.data.remote.RetrofitClient

class AuthRepository {

    suspend fun login(phone: String, password: String): Result<Pair<String, String>> {
        return try {
            val response = RetrofitClient.authApi.login(
                LoginRequest(phone, password)
            )

            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Result.success(Pair(body.token, body.role))
            } else {
                Result.failure(Exception("Login failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(name: String, phone: String, password: String, role: String): Result<String> {
        return try {
            val response = RetrofitClient.authApi.register(
                com.example.gigmarket.data.model.RegisterRequest(name, phone, password, role)
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.message)
            } else {
                Result.failure(Exception("Registration failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
