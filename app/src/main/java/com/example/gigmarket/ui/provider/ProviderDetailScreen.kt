package com.example.gigmarket.ui.provider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gigmarket.data.model.Provider
import com.example.gigmarket.viewmodel.ProviderDetailState
import com.example.gigmarket.viewmodel.ProviderDetailViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProviderDetailScreen(
    providerId: String,
    viewModel: ProviderDetailViewModel = viewModel(),
    onBack: () -> Unit,
    onBookClick: (Provider) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(providerId) {
        viewModel.fetchProvider(providerId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Provider Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when (val state = uiState) {
            is ProviderDetailState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ProviderDetailState.Success -> {
                val provider = state.provider
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Profile Header
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color.LightGray, RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = provider.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = provider.category,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFFFB400),
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "${provider.rating} (${provider.totalReviews} reviews)",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Bio
                    Text(text = "About", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(
                        text = provider.bio ?: "No description provided.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Skills
                    Text(text = "Skills", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    FlowRow(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        provider.skills.forEach { skill ->
                            SuggestionChip(onClick = {}, label = { Text(skill) })
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Languages
                    Text(text = "Languages", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(
                        text = provider.languages.joinToString(", "),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Rate & Availability
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Hourly Rate", style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    text = "₹${provider.hourlyRate}/hr",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Availability", style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    text = if (provider.isAvailable) "Available Now" else "Currently Busy",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = if (provider.isAvailable) Color(0xFF4CAF50) else Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { onBookClick(provider) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = provider.isAvailable,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Book Now", modifier = Modifier.padding(8.dp))
                    }
                }
            }
            is ProviderDetailState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.fetchProvider(providerId) }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}
