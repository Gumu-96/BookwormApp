package com.gumu.bookwormapp.domain.model

data class BookStats(
    val book: Book,
    val status: ReadingStatus = ReadingStatus.ON_QUEUE,
    val rating: Int = 0,
    val thoughts: String? = null,
    val id: String? = null
)
