package com.example.gigmarket.data.model

data class Booking(
    val _id: String,
    val userId: UserSummary?,
    val providerId: ProviderSummary?,
    val status: String, // Pending, Accepted, Completed, Cancelled
    val bookingDate: String,
    val address: String,
    val notes: String?
)

data class UserSummary(
    val _id: String,
    val name: String,
    val phone: String
)

data class ProviderSummary(
    val _id: String,
    val name: String,
    val category: String,
    val hourlyRate: Double
)

data class BookingRequest(
    val providerId: String,
    val address: String,
    val notes: String?
)

data class BookingResponse(
    val success: Boolean,
    val booking: Booking
)

data class BookingsListResponse(
    val success: Boolean,
    val bookings: List<Booking>
)
