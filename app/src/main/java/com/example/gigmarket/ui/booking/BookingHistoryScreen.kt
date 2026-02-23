package com.example.gigmarket.ui.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gigmarket.data.model.Booking
import com.example.gigmarket.viewmodel.BookingsListState
import com.example.gigmarket.viewmodel.BookingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryScreen(
    viewModel: BookingViewModel = viewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.bookingsListState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMyBookings()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Bookings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when (val state = uiState) {
            is BookingsListState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is BookingsListState.Success -> {
                if (state.bookings.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                        Text("No bookings found.")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(16.dp)
                    ) {
                        items(state.bookings) { booking ->
                            BookingItem(booking, onCancel = { viewModel.cancelBooking(booking._id) })
                        }
                    }
                }
            }
            is BookingsListState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.fetchMyBookings() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookingItem(booking: Booking, onCancel: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = booking.providerId?.name ?: booking.userId?.name ?: "Unknown",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = booking.status,
                    style = MaterialTheme.typography.labelLarge,
                    color = when (booking.status) {
                        "Accepted" -> Color(0xFF4CAF50)
                        "Pending" -> Color(0xFFFF9800)
                        "Cancelled" -> Color.Red
                        else -> Color.Gray
                    }
                )
            }
            Text(
                text = booking.providerId?.category ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: ${booking.bookingDate.take(10)}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Address: ${booking.address}", style = MaterialTheme.typography.bodySmall)
            
            if (booking.status == "Pending") {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
