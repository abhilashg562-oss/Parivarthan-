package com.example.gigmarket.data.model

data class Provider(
    val _id: String,
    val name: String,
    val phone: String,
    val skills: List<String>,
    val category: String,
    val hourlyRate: Double,
    val bio: String?,
    val photo: String?,
    val languages: List<String>,
    val rating: Double,
    val totalReviews: Int,
    val isVerified: Boolean,
    val isAvailable: Boolean,
    val location: LocationModel
)

data class LocationModel(
    val type: String,
    val coordinates: List<Double>, // [longitude, latitude]
    val address: String?
)

data class NearbyProvidersResponse(
    val success: Boolean,
    val count: Int,
    val providers: List<Provider>
)

data class ProviderResponse(
    val success: Boolean,
    val provider: Provider
)
