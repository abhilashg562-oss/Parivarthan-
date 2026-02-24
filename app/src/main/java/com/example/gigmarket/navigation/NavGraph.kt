package com.example.gigmarket.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gigmarket.ui.auth.LoginScreen
import com.example.gigmarket.ui.auth.RegisterScreen
import com.example.gigmarket.ui.dashboard.DashboardScreen
import com.example.gigmarket.ui.dashboard.ProviderDashboardScreen
import com.example.gigmarket.ui.provider.ProviderDetailScreen
import com.example.gigmarket.ui.booking.BookingScreen
import com.example.gigmarket.ui.booking.BookingHistoryScreen
import com.example.gigmarket.viewmodel.AuthViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object ProviderDetail : Screen("provider_detail/{providerId}") {
        fun createRoute(providerId: String) = "provider_detail/$providerId"
    }
    object Booking : Screen("booking/{providerId}/{providerName}") {
        fun createRoute(providerId: String, providerName: String) = "booking/$providerId/$providerName"
    }
    object BookingHistory : Screen("booking_history")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.Dashboard.route else Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = { 
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Dashboard.route) {
            val role by authViewModel.userRole.collectAsState()

            if (role == "provider") {
                ProviderDashboardScreen(
                    authViewModel = authViewModel,
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Dashboard.route) { inclusive = true }
                        }
                    }
                )
            } else {
                DashboardScreen(
                    authViewModel = authViewModel,
                    onProviderClick = { providerId ->
                        navController.navigate(Screen.ProviderDetail.createRoute(providerId))
                    },
                    onViewBookings = {
                        navController.navigate(Screen.BookingHistory.route)
                    },
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Dashboard.route) { inclusive = true }
                        }
                    }
                )
            }
        }
        composable(Screen.ProviderDetail.route) { backStackEntry ->
            val providerId = backStackEntry.arguments?.getString("providerId") ?: return@composable
            ProviderDetailScreen(
                providerId = providerId,
                onBack = { navController.popBackStack() },
                onBookClick = { provider ->
                    navController.navigate(Screen.Booking.createRoute(provider._id, provider.name))
                }
            )
        }
        composable(Screen.Booking.route) { backStackEntry ->
            val providerId = backStackEntry.arguments?.getString("providerId") ?: return@composable
            val providerName = backStackEntry.arguments?.getString("providerName") ?: ""
            BookingScreen(
                providerId = providerId,
                providerName = providerName,
                onBack = { navController.popBackStack() },
                onBookingSuccess = {
                    navController.navigate(Screen.BookingHistory.route) {
                        popUpTo(Screen.Dashboard.route)
                    }
                }
            )
        }
        composable(Screen.BookingHistory.route) {
            BookingHistoryScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

