package com.gumu.bookwormapp.presentation.navigation

sealed class Screen(val route: String) {
    private val baseRoute: String =
        route.substring(0, route.indexOf('/').takeIf { it > -1 } ?: route.length)

    object SignInScreen: Screen("sign_in_screen")
    object SignUpScreen: Screen("sign_up_screen")
    object HomeScreen: Screen("home_screen")
    object SearchScreen: Screen("search_screen")
    object BookStatsScreen: Screen("book_stats_screen/{$BOOK_STATS_ID_PARAM}")
    object ProfileScreen: Screen("profile_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(baseRoute)
            args.forEach {
                append("/$it")
            }
        }
    }

    companion object {
        const val BOOK_STATS_ID_PARAM = "book_stats_id"
    }
}
