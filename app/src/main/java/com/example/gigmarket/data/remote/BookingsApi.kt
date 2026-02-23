package com.example.gigmarket.data.remote

import com.example.gigmarket.data.model.BookingRequest
import com.example.gigmarket.data.model.BookingResponse
import com.example.gigmarket.data.model.BookingsListResponse
import retrofit2.Response
import retrofit2.http.*

interface BookingsApi {

    @POST("api/bookings")
    suspend fun createBooking(
        @Header("Authorization") token: String,
        @Body request: BookingRequest
    ): Response<BookingResponse>

    @GET("api/bookings")
    suspend fun getMyBookings(
        @Header("Authorization") token: String
    ): Response<BookingsListResponse>

    @GET("api/bookings/assigned")
    suspend fun getAssignedBookings(
        @Header("Authorization") token: String
    ): Response<BookingsListResponse>

    @PATCH("api/bookings/{id}/cancel")
    suspend fun cancelBooking(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<BookingResponse>
}
