package com.gumu.bookwormapp.presentation.ui.home

import com.gumu.bookwormapp.domain.model.BookStats

sealed class HomeEvent {
    object OnAddBookClick : HomeEvent()
    object OnAccountClick : HomeEvent()
    data class OnBookStatsClick(val bookStats: BookStats) : HomeEvent()
}
