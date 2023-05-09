package com.gumu.bookwormapp.domain.model

data class BookStats(
    val id: String,
    val book: Book,
    val status: ReadingStatus = ReadingStatus.ON_QUEUE,
    val rating: Int = 0,
    val thoughts: String = ""
)
