package com.gumu.bookwormapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.gumu.bookwormapp.presentation.ui.bookstats.BookStatsScreen
import com.gumu.bookwormapp.presentation.ui.bookstats.BookStatsViewModel
import com.gumu.bookwormapp.presentation.ui.common.ScreenWrapper
import com.gumu.bookwormapp.presentation.ui.home.HomeScreen
import com.gumu.bookwormapp.presentation.ui.home.HomeViewModel
import com.gumu.bookwormapp.presentation.ui.search.SearchScreen
import com.gumu.bookwormapp.presentation.ui.search.SearchViewModel
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
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                SignInScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
        composable(route = Screen.SignUpScreen.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                SignUpScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
        composable(route = Screen.HomeScreen.route) {
            val viewModel: HomeViewModel = hiltViewModel()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                HomeScreen(
                    onQueueList = viewModel.onQueueBooks.collectAsLazyPagingItems(),
                    readingList = viewModel.readingBooks.collectAsLazyPagingItems(),
                    readList = viewModel.readBooks.collectAsLazyPagingItems(),
                    onEvent = viewModel::onEvent
                )
            }
        }
        composable(route = Screen.SearchScreen.route) {
            val viewModel: SearchViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                SearchScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
        composable(
            route = Screen.BookStatsScreen.route,
            arguments = listOf(
                navArgument(Screen.BOOK_STATS_ID_PARAM) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: BookStatsViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                BookStatsScreen(
                    bookStatsId = it.arguments?.getString(Screen.BOOK_STATS_ID_PARAM),
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}
