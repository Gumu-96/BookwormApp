package com.gumu.bookwormapp.presentation.ui.bookstats

import com.gumu.bookwormapp.domain.model.Book
import com.gumu.bookwormapp.domain.model.ReadingStatus

data class BookStatsState(
    val isLoading: Boolean = false,
    val book: Book? = null,
    val rating: Int = 0,
    val thoughts: String? = null,
    val status: ReadingStatus = ReadingStatus.ON_QUEUE,
    val hasChanges: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val showLeaveDialog: Boolean = false,
    val savingChanges: Boolean = false
)
