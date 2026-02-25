package com.example.gigmarket.ui.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gigmarket.data.model.Booking
import com.example.gigmarket.ui.components.NeonCard
import com.example.gigmarket.ui.theme.*
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

    NeonTheme {
        Scaffold(
            containerColor = DarkBackground,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Booking History", color = NeonBlue, fontWeight = FontWeight.ExtraBold) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = DarkBackground.copy(alpha = 0.9f)
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(DarkBackground, Color(0xFF130026))
                        )
                    )
            ) {
                when (val state = uiState) {
                    is BookingsListState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = NeonBlue)
                        }
                    }
                    is BookingsListState.Success -> {
                        if (state.bookings.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No bookings found.", color = GrayText)
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 24.dp),
                                contentPadding = PaddingValues(vertical = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(state.bookings) { booking ->
                                    BookingItem(booking, onCancel = { viewModel.cancelBooking(booking._id) })
                                }
                            }
                        }
                    }
                    is BookingsListState.Error -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = state.message, color = NeonPink)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { viewModel.fetchMyBookings() },
                                    colors = ButtonDefaults.buttonColors(containerColor = NeonBlue)
                                ) {
                                    Text("Retry", color = Color.Black)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookingItem(booking: Booking, onCancel: () -> Unit) {
    val statusColor = when (booking.status) {
        "Accepted" -> NeonBlue
        "Pending" -> NeonPurple
        "Cancelled" -> NeonPink
        else -> GrayText
    }

    NeonCard(
        glowColor = statusColor
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = booking.providerId?.name ?: booking.userId?.name ?: "Unknown User",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                
                Surface(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = booking.status.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
            
            Text(
                text = (booking.providerId?.category ?: "Service").uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = statusColor.copy(alpha = 0.7f),
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Date:", color = GrayText, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = booking.bookingDate.take(10),
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Row(verticalAlignment = Alignment.Top) {
                Text(text = "Location:", color = GrayText, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = booking.address,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
            
            if (booking.status == "Pending") {
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(
                    onClick = onCancel,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("CANCEL BOOKING", color = NeonPink, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}


