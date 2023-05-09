package com.gumu.bookwormapp.data.util

import com.gumu.bookwormapp.data.remote.dto.BookItemDto
import com.gumu.bookwormapp.domain.model.Book

fun BookItemDto.toDomain() =
    Book(
        id = id,
        title = volumeInfo.title,
        authors = volumeInfo.authors,
        publishedDate = volumeInfo.publishedDate,
        categories = volumeInfo.categories,
        thumbnail = volumeInfo.imageLinks.thumbnail
    )
