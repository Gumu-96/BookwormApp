package com.gumu.bookwormapp.data.remote.dto

data class BookSearchDto(
    val totalItems: Int,
    val items: List<BookItemDto>?
)

data class BookItemDto(
    val id: String,
    val selfLink: String,
    val volumeInfo: BookItemInfoDto
)

data class BookItemInfoDto(
    val title: String,
    val authors: List<String>?,
    val publishedDate: String?,
    val description: String?,
    val categories: List<String>?,
    val imageLinks: BookImageLinksDto?
)

data class BookImageLinksDto(
    val smallThumbnail: String,
    val thumbnail: String
)
