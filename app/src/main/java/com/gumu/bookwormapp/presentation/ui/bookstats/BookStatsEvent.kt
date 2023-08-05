package com.gumu.bookwormapp.presentation.ui.bookstats

import com.gumu.bookwormapp.domain.model.ReadingStatus

sealed class BookStatsEvent {
    data class OnLoadStats(val statsId: String?) : BookStatsEvent()
    object OnBackClick : BookStatsEvent()
    object OnConfirmLeave : BookStatsEvent()
    object OnDeleteClick : BookStatsEvent()
    object OnConfirmDelete : BookStatsEvent()
    object OnDismissDialog : BookStatsEvent()
    object OnSaveChangesClick : BookStatsEvent()
    data class OnSetRating(val rating: Int) : BookStatsEvent()
    data class OnThoughtsChange(val thoughts: String) : BookStatsEvent()
    data class OnStatusChange(val status: ReadingStatus) : BookStatsEvent()
}
