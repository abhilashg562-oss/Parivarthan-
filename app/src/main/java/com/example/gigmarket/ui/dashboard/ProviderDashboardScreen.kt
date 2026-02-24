package com.example.gigmarket.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
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
import com.example.gigmarket.ui.booking.BookingItem
import com.example.gigmarket.ui.components.NeonCard
import com.example.gigmarket.ui.theme.*
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

    NeonTheme {
        Scaffold(
            containerColor = DarkBackground,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Provider Portal",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            ),
                            color = NeonPurple
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            authViewModel.logout()
                            onLogout()
                        }) {
                            Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = NeonPink)
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
                        .padding(horizontal = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Work Status",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    NeonCard(
                        glowColor = if (isAvailable) NeonBlue else GrayText
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = if (isAvailable) "Accepting New Gigs" else "Taking a Break",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = if (isAvailable) NeonBlue else GrayText,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (isAvailable) "Show up in search results" else "Hidden from customers",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = GrayText
                                )
                            }
                            Switch(
                                checked = isAvailable,
                                onCheckedChange = { isAvailable = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = NeonBlue,
                                    uncheckedThumbColor = GrayText,
                                    uncheckedTrackColor = Color(0xFF333333)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Text(
                        text = "Assigned Gigs",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    when (val state = bookingsState) {
                        is BookingsListState.Loading -> {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = NeonBlue)
                            }
                        }
                        is BookingsListState.Success -> {
                            if (state.bookings.isEmpty()) {
                                Text(
                                    "No assigned gigs yet. Keep your status active!",
                                    color = GrayText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(top = 16.dp)
                                )
                            } else {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(bottom = 24.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(state.bookings) { booking ->
                                        BookingItem(booking, onCancel = {}) 
                                    }
                                }
                            }
                        }
                        is BookingsListState.Error -> {
                            Text(text = state.message, color = NeonPink)
                        }
                    }
                }
            }
        }
    }
}


