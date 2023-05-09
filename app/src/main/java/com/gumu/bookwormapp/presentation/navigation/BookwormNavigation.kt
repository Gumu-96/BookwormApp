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
import com.gumu.bookwormapp.presentation.ui.home.HomeViewModel
import com.gumu.bookwormapp.presentation.ui.signin.SignInScreen
import com.gumu.bookwormapp.presentation.ui.signin.SignInViewModel
import com.gumu.bookwormapp.presentation.ui.signup.SignUpScreen
import com.gumu.bookwormapp.presentation.ui.signup.SignUpViewModel

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
            val viewModel: SignUpViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                SignUpScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
        composable(route = Screen.HomeScreen.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                HomeScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}
