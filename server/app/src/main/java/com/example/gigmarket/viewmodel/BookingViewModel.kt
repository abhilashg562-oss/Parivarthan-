package com.example.gigmarket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gigmarket.data.local.TokenManager
import com.example.gigmarket.data.model.Booking
import com.example.gigmarket.data.repository.BookingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class BookingState {
    object Idle : BookingState()
    object Loading : BookingState()
    data class Success(val message: String) : BookingState()
    data class Error(val message: String) : BookingState()
}

sealed class BookingsListState {
    object Loading : BookingsListState()
    data class Success(val bookings: List<Booking>) : BookingsListState()
    data class Error(val message: String) : BookingsListState()
}

class BookingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BookingRepository(TokenManager(application))

    private val _bookingState = MutableStateFlow<BookingState>(BookingState.Idle)
    val bookingState = _bookingState.asStateFlow()

    private val _bookingsListState = MutableStateFlow<BookingsListState>(BookingsListState.Loading)
    val bookingsListState = _bookingsListState.asStateFlow()

    fun createBooking(providerId: String, address: String, notes: String?) {
        viewModelScope.launch {
            _bookingState.value = BookingState.Loading
            val result = repository.createBooking(providerId, address, notes)
            result.onSuccess {
                _bookingState.value = BookingState.Success("Booking successful! 📅")
            }
            result.onFailure {
                _bookingState.value = BookingState.Error(it.message ?: "Booking failed")
            }
        }
    }

    fun fetchMyBookings() {
        viewModelScope.launch {
            _bookingsListState.value = BookingsListState.Loading
            val result = repository.getMyBookings()
            result.onSuccess {
                _bookingsListState.value = BookingsListState.Success(it)
            }
            result.onFailure {
                _bookingsListState.value = BookingsListState.Error(it.message ?: "Failed to fetch bookings")
            }
        }
    }

    fun fetchAssignedBookings() {
        viewModelScope.launch {
            _bookingsListState.value = BookingsListState.Loading
            val result = repository.getAssignedBookings()
            result.onSuccess {
                _bookingsListState.value = BookingsListState.Success(it)
            }
            result.onFailure {
                _bookingsListState.value = BookingsListState.Error(it.message ?: "Failed to fetch assigned bookings")
            }
        }
    }

    fun cancelBooking(id: String) {
        viewModelScope.launch {
            val result = repository.cancelBooking(id)
            result.onSuccess {
                fetchMyBookings() // Refresh list
            }
        }
    }
}
