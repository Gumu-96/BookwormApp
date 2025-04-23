package com.gumu.bookwormapp.presentation.ui.bookstats

import com.gumu.bookwormapp.domain.model.Book
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus

data class BookStatsState(
    val initialStats: BookStats? = null,
    val isLoading: Boolean = false,
    val book: Book? = null,
    val rating: Int = 0,
    val thoughts: String? = null,
    val status: ReadingStatus = ReadingStatus.ON_QUEUE,
    val showDeleteDialog: Boolean = false,
    val showLeaveDialog: Boolean = false,
    val savingChanges: Boolean = false
) {
    val hasChanges = initialStats?.rating != rating ||
            initialStats.thoughts != thoughts ||
            initialStats.status != status
}
