package com.example.gigmarket.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gigmarket.data.model.Provider

@Composable
fun ProviderCard(
    provider: Provider,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder for Image
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = provider.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = provider.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFB400),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${provider.rating} (${provider.totalReviews} reviews)",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "₹${provider.hourlyRate}/hr",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                if (provider.isVerified) {
                    Text(
                        text = "Verified",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF4CAF50)
                    )
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
    ScrollableTabRow(
        selectedTabIndex = categories.indexOf(selectedCategory),
        edgePadding = 16.dp,
        containerColor = Color.Transparent,
        divider = {},
        indicator = {}
    ) {
        categories.forEach { category ->
            val selected = category == selectedCategory
            FilterChip(
                selected = selected,
                onClick = { onCategorySelected(category) },
                label = { Text(category) },
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}
