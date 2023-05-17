package com.gumu.bookwormapp.data.remote.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import com.gumu.bookwormapp.data.remote.RemoteConstants
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
    val finishedReading: Timestamp? = null,
    @ServerTimestamp
    val createdAt: Timestamp? = null
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

fun BookStatsDto.toDomain(id: String?) =
    BookStats(
        book = book.toDomain(bookId),
        status = status,
        rating = rating,
        thoughts = thoughts,
        id = id
    )

fun BookStats.toUpdateMap() =
    mapOf(
        RemoteConstants.BOOK_STATUS_FIELD to status,
        RemoteConstants.BOOK_RATING_FIELD to rating,
        RemoteConstants.BOOK_THOUGHTS_FIELD to thoughts,
        when (status) {
            ReadingStatus.READING -> RemoteConstants.BOOK_STARTED_READING_FIELD to FieldValue.serverTimestamp()
            ReadingStatus.READ -> RemoteConstants.BOOK_FINISHED_READING_FIELD to FieldValue.serverTimestamp()
            else -> {
                RemoteConstants.BOOK_STARTED_READING_FIELD to null
                RemoteConstants.BOOK_STARTED_READING_FIELD to null
            }
        }
    )
