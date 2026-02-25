package com.example.gigmarket.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
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
import com.example.gigmarket.ui.dashboard.components.*
import com.example.gigmarket.ui.theme.*
import com.example.gigmarket.viewmodel.AuthViewModel
import com.example.gigmarket.viewmodel.DashboardState
import com.example.gigmarket.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    authViewModel: AuthViewModel,
    dashboardViewModel: DashboardViewModel = viewModel(),
    onProviderClick: (String) -> Unit,
    onViewBookings: () -> Unit,
    onLogout: () -> Unit
) {
    val uiState by dashboardViewModel.uiState.collectAsState()
    val categories by dashboardViewModel.categories.collectAsState()
    val selectedCategory by dashboardViewModel.selectedCategory.collectAsState()

    NeonTheme {
        Scaffold(
            containerColor = DarkBackground,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "GigMarket",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            ),
                            color = NeonBlue
                        )
                    },
                    actions = {
                        IconButton(onClick = onViewBookings) {
                            Icon(Icons.Default.List, contentDescription = "Bookings", tint = NeonPurple)
                        }
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
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Explore Services",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                    )

                    CategoryFilter(
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { dashboardViewModel.onCategorySelected(it) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = NeonBlue.copy(alpha = 0.3f),
                        thickness = 0.5.dp
                    )

                    when (val state = uiState) {
                        is DashboardState.Loading -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = NeonBlue)
                            }
                        }
                        is DashboardState.Success -> {
                            if (state.providers.isEmpty()) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text("No providers found.", color = GrayText)
                                }
                            } else {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp),
                                    contentPadding = PaddingValues(bottom = 24.dp, top = 8.dp)
                                ) {
                                    items(state.providers) { provider ->
                                        ProviderCard(
                                            provider = provider,
                                            onClick = { onProviderClick(provider._id) }
                                        )
                                    }
                                }
                            }
                        }
                        is DashboardState.Error -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = state.message, color = NeonPink)
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = { dashboardViewModel.fetchProviders(selectedCategory) },
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
}


