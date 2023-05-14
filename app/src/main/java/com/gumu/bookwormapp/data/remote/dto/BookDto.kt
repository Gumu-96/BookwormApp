package com.gumu.bookwormapp.data.remote.dto

import com.gumu.bookwormapp.domain.model.Book

data class BookDto (
    val title: String,
    val authors: List<String>?,
    val publishedDate: String?,
    val description: String?,
    val categories: List<String>?,
    val thumbnail: String?
) {
    constructor(): this("", null, null, null, null, null)
}

fun Book.toDto() =
    BookDto(
        title = title,
        authors = authors,
        publishedDate = publishedDate,
        description = description,
        categories = categories,
        thumbnail = thumbnail
    )

fun BookDto.toDomain(bookId: String) =
    Book(
        id = bookId,
        title = title,
        authors = authors,
        publishedDate = publishedDate,
        description = description,
        categories = categories,
        thumbnail = thumbnail
    )
