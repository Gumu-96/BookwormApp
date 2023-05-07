package com.gumu.bookwormapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gumu.bookwormapp.presentation.ui.common.ScreenWrapper
import com.gumu.bookwormapp.presentation.ui.home.HomeScreen
import com.gumu.bookwormapp.presentation.ui.signin.SignInScreen
import com.gumu.bookwormapp.presentation.ui.signin.SignInViewModel
import com.gumu.bookwormapp.presentation.ui.signup.SignUpScreen

@Composable
fun BookwormNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SignInScreen.route) {
        composable(route = Screen.SignInScreen.route) {
            val viewModel: SignInViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                SignInScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
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
