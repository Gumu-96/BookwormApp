package com.gumu.bookwormapp.presentation.ui.bookstats

import com.gumu.bookwormapp.domain.model.ReadingStatus

sealed class BookStatsIntent {
    data object OnBackClick : BookStatsIntent()
    data object OnConfirmLeave : BookStatsIntent()
    data object OnDeleteClick : BookStatsIntent()
    data object OnConfirmDelete : BookStatsIntent()
    data object OnDismissDialog : BookStatsIntent()
    data object OnSaveChangesClick : BookStatsIntent()
    data class OnSetRating(val rating: Int) : BookStatsIntent()
    data class OnThoughtsChange(val thoughts: String) : BookStatsIntent()
    data class OnStatusChange(val status: ReadingStatus) : BookStatsIntent()
}
