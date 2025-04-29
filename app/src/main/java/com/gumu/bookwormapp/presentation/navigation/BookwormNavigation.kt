package com.gumu.bookwormapp.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.presentation.animation.AnimationConstants.SCREEN_TRANSITION_DELAY_MILLIS
import com.gumu.bookwormapp.presentation.animation.AnimationConstants.SCREEN_TRANSITION_DURATION_MILLIS
import com.gumu.bookwormapp.presentation.animation.ScaleTransitionDirection
import com.gumu.bookwormapp.presentation.animation.fadeInScreen
import com.gumu.bookwormapp.presentation.animation.fadeOutScreen
import com.gumu.bookwormapp.presentation.animation.scaleIntoContainer
import com.gumu.bookwormapp.presentation.animation.scaleOutOfContainer
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
import kotlin.reflect.typeOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BookwormNavigation() {
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = Screen.SignInScreen,
            enterTransition = { fadeInScreen() },
            exitTransition = { fadeOutScreen() }
        ) {
            composable<Screen.SignInScreen> {
                val viewModel: SignInViewModel = hiltViewModel()

                ScreenWrapper(viewModel = viewModel, navController = navController) {
                    SignInScreen(
                        state = it,
                        onIntent = viewModel::onIntent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable
                    )
                }
            }
            composable<Screen.SignUpScreen> {
                val viewModel: SignUpViewModel = hiltViewModel()

                ScreenWrapper(viewModel = viewModel, navController = navController) {
                    SignUpScreen(
                        state = it,
                        onIntent = viewModel::onIntent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable
                    )
                }
            }
            composable<Screen.HomeScreen>(
                exitTransition = {
                    when (targetState.destination.route) {
                        Screen.SearchScreen::class.qualifiedName -> {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = tween(
                                    SCREEN_TRANSITION_DURATION_MILLIS,
                                    SCREEN_TRANSITION_DELAY_MILLIS
                                )
                            )
                        }
                        Screen.BookStatsScreen::class.qualifiedName -> {
                            scaleOutOfContainer(ScaleTransitionDirection.Inwards)
                        }
                        else -> {
                            fadeOutScreen()
                        }
                    }
                },
                popEnterTransition = {
                    when (initialState.destination.route) {
                        Screen.SearchScreen::class.qualifiedName -> {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = tween(
                                    SCREEN_TRANSITION_DURATION_MILLIS,
                                    SCREEN_TRANSITION_DELAY_MILLIS
                                )
                            )
                        }
                        Screen.BookStatsScreen::class.qualifiedName -> {
                            scaleIntoContainer(ScaleTransitionDirection.Outwards)
                        }
                        else -> fadeInScreen()

                    }
                }
            ) {
                val viewModel: HomeViewModel = hiltViewModel()

                ScreenWrapper(viewModel = viewModel, navController = navController) {
                    HomeScreen(
                        onQueueList = viewModel.onQueueBooks.collectAsLazyPagingItems(),
                        readingList = viewModel.readingBooks.collectAsLazyPagingItems(),
                        readList = viewModel.readBooks.collectAsLazyPagingItems(),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable,
                        onIntent = viewModel::onIntent
                    )
                }
            }
            composable<Screen.SearchScreen>(
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(
                            SCREEN_TRANSITION_DURATION_MILLIS,
                            SCREEN_TRANSITION_DELAY_MILLIS
                        )
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(
                            SCREEN_TRANSITION_DURATION_MILLIS,
                            SCREEN_TRANSITION_DELAY_MILLIS
                        )
                    )
                }
            ) {
                val viewModel: SearchViewModel = hiltViewModel()

                ScreenWrapper(viewModel = viewModel, navController = navController) {
                    SearchScreen(
                        state = it,
                        onIntent = viewModel::onIntent
                    )
                }
            }
            composable<Screen.BookStatsScreen>(
                typeMap = mapOf(typeOf<BookStats>() to BookwormNavType.BookStatsType),
                enterTransition = { scaleIntoContainer(ScaleTransitionDirection.Outwards) },
                popExitTransition = { scaleOutOfContainer(ScaleTransitionDirection.Inwards) }
            ) {
                val viewModel: BookStatsViewModel = hiltViewModel()

                ScreenWrapper(viewModel = viewModel, navController = navController) { state ->
                    BookStatsScreen(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable,
                        state = state,
                        onIntent = viewModel::onIntent
                    )
                }
            }
        }
    }
}
