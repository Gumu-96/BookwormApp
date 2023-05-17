package com.gumu.bookwormapp.domain.model

import java.util.Date

data class BookStats(
    val book: Book,
    val status: ReadingStatus = ReadingStatus.ON_QUEUE,
    val rating: Int = 0,
    val thoughts: String? = null,
    val id: String? = null,
    val startedReading: Date? = null,
    val finishedReading: Date? = null,
)
