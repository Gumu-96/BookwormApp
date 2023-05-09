package com.gumu.bookwormapp.domain.model

data class User(
    val id: String,
    val firstname: String,
    val lastname: String,
    val stats: List<BookStats>
)
