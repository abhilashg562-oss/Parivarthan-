package com.example.gigmarket.ui.auth

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
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
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onLoginSuccess()
        }
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
                enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(
                    initialOffsetY = { it / 10 },
                    animationSpec = tween(1000)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Glowing logo text
                    Text(
                        text = "GigMarket",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp
                        ),
                        color = NeonBlue,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Text(
                        text = "THE FUTURE OF GIGS",
                        style = MaterialTheme.typography.labelMedium,
                        color = NeonPurple,
                        modifier = Modifier.padding(bottom = 48.dp)
                    )

                    NeonTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Phone Number"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    NeonTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    if (authState is AuthState.Loading) {
                        CircularProgressIndicator(color = NeonBlue)
                    } else {
                        NeonButton(
                            text = "LOGIN",
                            onClick = { viewModel.login(phone, password) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    if (authState is AuthState.Error) {
                        Text(
                            text = (authState as AuthState.Error).message,
                            color = NeonPink,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    TextButton(onClick = onNavigateToRegister) {
                        Row {
                            Text(
                                "Don't have an account? ",
                                color = GrayText
                            )
                            Text(
                                "Register",
                                color = NeonPink,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}


