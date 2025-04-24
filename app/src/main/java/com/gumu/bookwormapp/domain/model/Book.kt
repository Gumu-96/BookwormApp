package com.gumu.bookwormapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val publishedDate: String?,
    val description: String?,
    val categories: List<String>?,
    val thumbnail: String?
)
