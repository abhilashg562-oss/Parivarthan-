package com.example.gigmarket.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gigmarket.ui.booking.BookingItem
import com.example.gigmarket.viewmodel.AuthViewModel
import com.example.gigmarket.viewmodel.BookingViewModel
import com.example.gigmarket.viewmodel.BookingsListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderDashboardScreen(
    authViewModel: AuthViewModel,
    bookingViewModel: BookingViewModel = viewModel(),
    onLogout: () -> Unit
) {
    var isAvailable by remember { mutableStateOf(true) }
    val bookingsState by bookingViewModel.bookingsListState.collectAsState()

    LaunchedEffect(Unit) {
        bookingViewModel.fetchAssignedBookings()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GigMarket Provider") },
                actions = {
                    TextButton(onClick = {
                        authViewModel.logout()
                        onLogout()
                    }) {
                        Text("Logout")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Status", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = if (isAvailable) "Accepting Gigs" else "Not Available",
                            color = if (isAvailable) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    }
                    Switch(
                        checked = isAvailable,
                        onCheckedChange = { isAvailable = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Text(text = "Assigned Gigs", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            when (val state = bookingsState) {
                is BookingsListState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                is BookingsListState.Success -> {
                    if (state.bookings.isEmpty()) {
                        Text("No assigned gigs yet.", modifier = Modifier.padding(top = 16.dp))
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.bookings) { booking ->
                                // Reuse BookingItem but maybe logic is slightly different for providers
                                BookingItem(booking, onCancel = {}) 
                            }
                        }
                    }
                }
                is BookingsListState.Error -> Text(text = state.message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
