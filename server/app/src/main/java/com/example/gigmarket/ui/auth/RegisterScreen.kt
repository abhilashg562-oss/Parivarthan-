package com.example.gigmarket.ui.auth

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gigmarket.ui.components.NeonButton
import com.example.gigmarket.ui.components.NeonTextField
import com.example.gigmarket.ui.theme.*
import com.example.gigmarket.viewmodel.AuthState
import com.example.gigmarket.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("customer") }
    val authState by viewModel.authState.collectAsState()

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    NeonTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(DarkBackground, Color(0xFF1A0033))
                    )
                )
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(800)) + slideInVertically(
                    initialOffsetY = { 50 },
                    animationSpec = tween(800)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Join the Market",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = NeonBlue,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Text(
                        text = "CREATE YOUR ACCOUNT",
                        style = MaterialTheme.typography.labelMedium,
                        color = NeonPurple,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    NeonTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Full Name"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    NeonTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Phone Number"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    NeonTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = role == "customer",
                            onClick = { role = "customer" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = NeonPink,
                                unselectedColor = GrayText
                            )
                        )
                        Text("Customer", color = Color.White, modifier = Modifier.clickable { role = "customer" })
                        Spacer(modifier = Modifier.width(24.dp))
                        RadioButton(
                            selected = role == "provider",
                            onClick = { role = "provider" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = NeonPink,
                                unselectedColor = GrayText
                            )
                        )
                        Text("Provider", color = Color.White, modifier = Modifier.clickable { role = "provider" })
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    if (authState is AuthState.Loading) {
                        CircularProgressIndicator(color = NeonBlue)
                    } else {
                        NeonButton(
                            text = "REGISTER",
                            onClick = { viewModel.register(name, phone, password, role) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    if (authState is AuthState.Error) {
                        Text(
                            text = (authState as AuthState.Error).message,
                            color = NeonPink,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    } else if (authState is AuthState.Success) {
                        Text(
                            text = (authState as AuthState.Success).message,
                            color = NeonBlue,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    TextButton(onClick = onNavigateToLogin) {
                        Row {
                            Text("Already have an account? ", color = GrayText)
                            Text("Login", color = NeonPink, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}


