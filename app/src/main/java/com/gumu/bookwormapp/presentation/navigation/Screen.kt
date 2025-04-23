package com.gumu.bookwormapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object SignInScreen : Screen

    @Serializable
    data object SignUpScreen : Screen

    @Serializable
    data object HomeScreen : Screen

    @Serializable
    data object SearchScreen : Screen

    @Serializable
    data class BookStatsScreen(val statsId: String) : Screen

    @Serializable
    data object UserStatsScreen : Screen
}
