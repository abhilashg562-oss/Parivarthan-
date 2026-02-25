package com.example.gigmarket.ui.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.gigmarket.ui.components.NeonButton
import com.example.gigmarket.ui.components.NeonTextField
import com.example.gigmarket.ui.theme.*
import com.example.gigmarket.viewmodel.BookingState
import com.example.gigmarket.viewmodel.BookingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    providerId: String,
    providerName: String,
    viewModel: BookingViewModel = viewModel(),
    onBack: () -> Unit,
    onBookingSuccess: () -> Unit
) {
    var address by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val bookingState by viewModel.bookingState.collectAsState()

    LaunchedEffect(bookingState) {
        if (bookingState is BookingState.Success) {
            onBookingSuccess()
        }
    }

    NeonTheme {
        Scaffold(
            containerColor = DarkBackground,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Book Service",
                            color = NeonBlue,
                            fontWeight = FontWeight.ExtraBold
                        )
                    },
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Booking with $providerName",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Text(
                        text = "SECURE YOUR GIG",
                        style = MaterialTheme.typography.labelSmall,
                        color = NeonPurple,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    NeonTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = "Service Location / Address"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    NeonTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = "Notes for the Provider (Optional)"
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    if (bookingState is BookingState.Loading) {
                        CircularProgressIndicator(color = NeonBlue)
                    } else {
                        NeonButton(
                            text = "CONFIRM BOOKING",
                            onClick = { viewModel.createBooking(providerId, address, notes) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = address.isNotBlank()
                        )
                    }

                    if (bookingState is BookingState.Error) {
                        Text(
                            text = (bookingState as BookingState.Error).message,
                            color = NeonPink,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "Your booking will be sent to the provider for approval.",
                        style = MaterialTheme.typography.bodySmall,
                        color = GrayText,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}


