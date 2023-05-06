package com.gumu.bookwormapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gumu.bookwormapp.presentation.ui.home.HomeScreen
import com.gumu.bookwormapp.presentation.ui.signin.SignInScreen
import com.gumu.bookwormapp.presentation.ui.signup.SignUpScreen

@Composable
fun BookwormNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SignInScreen.route) {
        composable(route = Screen.SignInScreen.route) {
            SignInScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.SignInScreen.route) { inclusive = true }
                    }
                },
                onNavigateToSignUpScreen = { navController.navigate(Screen.SignUpScreen.route) }
            )
        }
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen()
        }
    }
}
