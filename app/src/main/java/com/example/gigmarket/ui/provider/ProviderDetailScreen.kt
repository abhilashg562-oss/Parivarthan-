package com.example.gigmarket.ui.provider

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gigmarket.data.model.Provider
import com.example.gigmarket.ui.components.NeonButton
import com.example.gigmarket.ui.components.NeonCard
import com.example.gigmarket.ui.theme.*
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

    NeonTheme {
        Scaffold(
            containerColor = DarkBackground,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Provider Info", color = NeonBlue, fontWeight = FontWeight.ExtraBold) },
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
                    is ProviderDetailState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = NeonBlue)
                        }
                    }
                    is ProviderDetailState.Success -> {
                        val provider = state.provider
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(24.dp)
                        ) {
                            // Profile Header
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(90.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(
                                            brush = Brush.linearGradient(
                                                listOf(NeonBlue.copy(alpha = 0.2f), NeonPurple.copy(alpha = 0.2f))
                                            )
                                        )
                                        .border(
                                            width = 1.dp, 
                                            brush = Brush.linearGradient(listOf(NeonBlue, NeonPurple)),
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = provider.name.firstOrNull()?.toString() ?: "G",
                                        color = NeonBlue,
                                        style = MaterialTheme.typography.displaySmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                
                                Spacer(modifier = Modifier.width(20.dp))
                                
                                Column {
                                    Text(
                                        text = provider.name,
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 0.5.sp
                                        ),
                                        color = Color.White
                                    )
                                    Text(
                                        text = provider.category.uppercase(),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = NeonBlue,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color(0xFFFFD700),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = "${provider.rating}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = Color.White,
                                            modifier = Modifier.padding(start = 4.dp),
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = " (${provider.totalReviews} reviews)",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = GrayText
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            // Stats section in a NeonCard
                            NeonCard(glowColor = NeonPurple) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("RATE", color = GrayText, style = MaterialTheme.typography.labelSmall)
                                        Text("₹${provider.hourlyRate}/hr", color = NeonPink, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                                    }
                                    VerticalDivider(modifier = Modifier.height(40.dp), color = Color(0xFF333333))
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("STATUS", color = GrayText, style = MaterialTheme.typography.labelSmall)
                                        Text(
                                            if (provider.isAvailable) "READY" else "BUSY",
                                            color = if (provider.isAvailable) NeonBlue else NeonPink,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 20.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            // About
                            Text(text = "BIO", style = MaterialTheme.typography.labelLarge, color = NeonPurple, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = provider.bio ?: "No bio description available for this provider yet.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                lineHeight = 24.sp
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            // Skills
                            Text(text = "SKILLS & EXPERTISE", style = MaterialTheme.typography.labelLarge, color = NeonPurple, fontWeight = FontWeight.Bold)
                            FlowRow(
                                modifier = Modifier.padding(top = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                provider.skills.forEach { skill ->
                                    Surface(
                                        color = NeonBlue.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.3f))
                                    ) {
                                        Text(
                                            text = skill,
                                            color = NeonBlue,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            if (provider.languages.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(32.dp))
                                Text(text = "COMMUNICATION", style = MaterialTheme.typography.labelLarge, color = NeonPurple, fontWeight = FontWeight.Bold)
                                Text(
                                    text = provider.languages.joinToString(", "),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(48.dp))

                            NeonButton(
                                text = "BOOK SERVICE NOW",
                                onClick = { onBookClick(provider) },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = provider.isAvailable
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                    is ProviderDetailState.Error -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = state.message, color = NeonPink)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { viewModel.fetchProvider(providerId) },
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


