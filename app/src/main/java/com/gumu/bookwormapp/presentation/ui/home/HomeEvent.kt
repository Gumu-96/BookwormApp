package com.gumu.bookwormapp.presentation.ui.home

sealed class HomeEvent {
    object OnAddBookClick : HomeEvent()
    object OnAccountClick : HomeEvent()
    data class OnBookStatsClick(val bookStatsId: String) : HomeEvent()
}
