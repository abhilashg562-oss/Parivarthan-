package com.example.gigmarket.ui.booking

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book $providerName") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Please provide your details for the booking.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Service Address") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Add Notes (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g. Please bring extra tools") },
                minLines = 2
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (bookingState is BookingState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { viewModel.createBooking(providerId, address, notes) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = address.isNotBlank()
                ) {
                    Text("Confirm Booking")
                }
            }

            if (bookingState is BookingState.Error) {
                Text(
                    text = (bookingState as BookingState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
