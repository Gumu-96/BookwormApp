package com.gumu.bookwormapp.domain.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val publishedDate: String?,
    val categories: List<String>?,
    val thumbnail: String?
)
