package com.example.gigmarket.ui.dashboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gigmarket.data.model.Provider
import com.example.gigmarket.ui.components.NeonCard
import com.example.gigmarket.ui.theme.*

@Composable
fun ProviderCard(
    provider: Provider,
    onClick: () -> Unit
) {
    NeonCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        onClick = onClick,
        glowColor = if (provider.isVerified) NeonBlue else NeonPurple
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Stylized profile placeholder
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.linearGradient(
                            listOf(NeonBlue.copy(alpha = 0.2f), NeonPurple.copy(alpha = 0.2f))
                        )
                    )
                    .border(
                        width = 1.dp, 
                        brush = Brush.linearGradient(listOf(NeonBlue, NeonPurple)),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = provider.name.firstOrNull()?.toString() ?: "G",
                    color = NeonBlue,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = provider.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = Color.White
                )
                Text(
                    text = provider.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonBlue,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "${provider.rating}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Text(
                        text = " (${provider.totalReviews})",
                        style = MaterialTheme.typography.bodySmall,
                        color = GrayText
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "₹${provider.hourlyRate}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = NeonPink
                )
                Text(
                    text = "/hr",
                    style = MaterialTheme.typography.labelSmall,
                    color = GrayText
                )
                if (provider.isVerified) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        color = NeonBlue.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(100.dp),
                        border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = "VERIFIED",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                            color = NeonBlue,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryFilter(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onCategorySelected(category) },
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) NeonPurple else Color(0xFF1E1E1E),
                border = BorderStroke(1.dp, if (isSelected) NeonPurple else Color(0xFF333333))
            ) {
                Text(
                    text = category,
                    color = if (isSelected) Color.White else GrayText,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}


