package com.gumu.bookwormapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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

    NavHost(navController = navController, startDestination = Screen.SignInScreen) {
        composable<Screen.SignInScreen> {
            val viewModel: SignInViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                SignInScreen(
                    state = state,
                    onIntent = viewModel::onIntent
                )
            }
        }
        composable<Screen.SignUpScreen> {
            val viewModel: SignUpViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                SignUpScreen(
                    state = state,
                    onIntent = viewModel::onIntent
                )
            }
        }
        composable<Screen.HomeScreen> {
            val viewModel: HomeViewModel = hiltViewModel()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                HomeScreen(
                    onQueueList = viewModel.onQueueBooks.collectAsLazyPagingItems(),
                    readingList = viewModel.readingBooks.collectAsLazyPagingItems(),
                    readList = viewModel.readBooks.collectAsLazyPagingItems(),
                    onIntent = viewModel::onIntent
                )
            }
        }
        composable<Screen.SearchScreen> {
            val viewModel: SearchViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                SearchScreen(
                    state = state,
                    onIntent = viewModel::onIntent
                )
            }
        }
        composable<Screen.BookStatsScreen> {
            val viewModel: BookStatsViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            val args = it.toRoute<Screen.BookStatsScreen>()

            ScreenWrapper(viewModel = viewModel, navController = navController) {
                BookStatsScreen(
                    bookStatsId = args.statsId,
                    state = state,
                    onIntent = viewModel::onIntent
                )
            }
        }
    }
}
