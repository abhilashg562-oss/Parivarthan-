package com.example.gigmarket.data.repository

import com.example.gigmarket.data.local.TokenManager
import com.example.gigmarket.data.model.Booking
import com.example.gigmarket.data.model.BookingRequest
import com.example.gigmarket.data.remote.RetrofitClient
import kotlinx.coroutines.flow.first

class BookingRepository(private val tokenManager: TokenManager) {

    private suspend fun getAuthToken(): String {
        val token = tokenManager.getToken().first() ?: ""
        return "Bearer $token"
    }

    suspend fun createBooking(providerId: String, address: String, notes: String?): Result<Booking> {
        return try {
            val response = RetrofitClient.bookingsApi.createBooking(
                getAuthToken(),
                BookingRequest(providerId, address, notes)
            )
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.booking)
            } else {
                Result.failure(Exception("Booking failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMyBookings(): Result<List<Booking>> {
        return try {
            val response = RetrofitClient.bookingsApi.getMyBookings(getAuthToken())
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.bookings)
            } else {
                Result.failure(Exception("Failed to fetch bookings: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAssignedBookings(): Result<List<Booking>> {
        return try {
            val response = RetrofitClient.bookingsApi.getAssignedBookings(getAuthToken())
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.bookings)
            } else {
                Result.failure(Exception("Failed to fetch assigned bookings: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun cancelBooking(id: String): Result<Booking> {
        return try {
            val response = RetrofitClient.bookingsApi.cancelBooking(getAuthToken(), id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.booking)
            } else {
                Result.failure(Exception("Cancel failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
