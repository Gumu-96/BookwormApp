package com.gumu.bookwormapp.presentation.animation

data class BookStatsSharedElementKey(
    val bookId: String?,
    val type: BookStatsSharedElementType
)

enum class BookStatsSharedElementType {
    Bounds,
    Image,
    BookTitle,
    Author,
    Status
}
