package com.example.gigmarket.repository


import com.example.gigmarket.network.LoginRequest
import com.example.gigmarket.network.RetrofitClient

class AuthRepository {

    suspend fun login(phone: String, password: String): Result<String> {

        return try {

            val response = RetrofitClient.api.login(
                LoginRequest(phone, password)
            )

            if (response.isSuccessful && response.body() != null) {

                val token = response.body()!!.token
                Result.success(token)
            } else {
                Result.failure(Exception("Invalid Credentials"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}