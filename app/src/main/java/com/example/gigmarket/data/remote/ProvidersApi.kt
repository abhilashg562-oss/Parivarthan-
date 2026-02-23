package com.example.gigmarket.data.remote

import com.example.gigmarket.data.model.NearbyProvidersResponse
import com.example.gigmarket.data.model.Provider
import com.example.gigmarket.data.model.ProviderResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProvidersApi {

    @GET("api/providers/nearby")
    suspend fun getNearbyProviders(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radius: Int = 5000,
        @Query("category") category: String? = null
    ): Response<NearbyProvidersResponse>

    @GET("api/providers/{id}")
    suspend fun getProviderById(
        @Path("id") id: String
    ): Response<ProviderResponse>
}
