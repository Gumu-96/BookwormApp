package com.gumu.bookwormapp.data.remote.dto

import com.gumu.bookwormapp.domain.model.Book

data class BookDto(
    val title: String = "",
    val authors: List<String>? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val categories: List<String>? = null,
    val thumbnail: String? = null
)

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
