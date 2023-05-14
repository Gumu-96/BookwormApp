package com.gumu.bookwormapp.data.remote.dto

import com.google.firebase.Timestamp
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus

data class BookStatsDto(
    val userId: String,
    val bookId: String,
    val book: BookDto,
    val rating: Int,
    val status: ReadingStatus,
    val thoughts: String?,
    val startedReading: Timestamp? = null,
    val finishedReading: Timestamp? = null
) {
    constructor(): this("", "", BookDto(), 0, ReadingStatus.ON_QUEUE, null, null, null)
}

fun BookStats.toDto(userId: String) =
    BookStatsDto(
        userId = userId,
        bookId = book.id,
        book = book.toDto(),
        rating = rating,
        status = status,
        thoughts = thoughts
    )

fun BookStatsDto.toDomain() =
    BookStats(
        book = book.toDomain(bookId),
        status = status,
        rating = rating,
        thoughts = thoughts
    )
