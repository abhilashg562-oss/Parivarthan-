package com.example.gigmarket.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gigmarket.ui.dashboard.components.CategoryFilter
import com.example.gigmarket.ui.dashboard.components.ProviderCard
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GigMarket") },
                actions = {
                    TextButton(onClick = onViewBookings) {
                        Text("My Bookings")
                    }
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
        ) {
            CategoryFilter(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { dashboardViewModel.onCategorySelected(it) }
            )

            when (val state = uiState) {
                is DashboardState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is DashboardState.Success -> {
                    if (state.providers.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No providers found in this category.")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
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
                            Text(text = state.message, color = MaterialTheme.colorScheme.error)
                            Button(onClick = { dashboardViewModel.fetchProviders(selectedCategory) }) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}
