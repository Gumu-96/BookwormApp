package com.gumu.bookwormapp.presentation.ui.home

import com.gumu.bookwormapp.domain.model.BookStats

sealed class HomeIntent {
    data object OnAddBookClick : HomeIntent()
    data object OnAccountClick : HomeIntent()
    data class OnBookStatsClick(val bookStats: BookStats) : HomeIntent()
}
