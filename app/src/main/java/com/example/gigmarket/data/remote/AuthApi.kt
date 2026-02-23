package com.example.gigmarket.data.remote

import com.example.gigmarket.data.model.LoginRequest
import com.example.gigmarket.data.model.LoginResponse
import com.example.gigmarket.data.model.RegisterRequest
import com.example.gigmarket.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}
