package com.example.gigmarket.data.repository

import com.example.gigmarket.data.model.NearbyProvidersResponse
import com.example.gigmarket.data.model.Provider
import com.example.gigmarket.data.model.ProviderResponse
import com.example.gigmarket.data.remote.RetrofitClient

class ProviderRepository {

    suspend fun getNearbyProviders(
        lat: Double,
        lng: Double,
        radius: Int = 5000,
        category: String? = null
    ): Result<List<Provider>> {
        return try {
            val response = RetrofitClient.providersApi.getNearbyProviders(lat, lng, radius, category)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.providers)
            } else {
                Result.failure(Exception("Failed to fetch providers: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProviderById(id: String): Result<Provider> {
        return try {
            val response = RetrofitClient.providersApi.getProviderById(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.provider)
            } else {
                Result.failure(Exception("Failed to fetch provider: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
