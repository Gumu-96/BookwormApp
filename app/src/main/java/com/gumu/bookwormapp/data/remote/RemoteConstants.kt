package com.gumu.bookwormapp.data.remote

import com.gumu.bookwormapp.domain.common.AppError
import com.gumu.bookwormapp.domain.common.AppResult

object RemoteConstants {
    val UNEXPECTED_ERROR = AppResult.Failure(AppError(cause = Throwable("Unexpected response")))

    // Firebase
    const val USERS_COLLECTION = "users"
    const val BOOKS_COLLECTION = "books"
    const val BOOK_STATS_COLLECTION = "book_stats"

    // Google's Books API
    const val DEFAULT_PAGE_SIZE = 20 // Max allowed value is 40 according to Google's docs
    const val BOOKS_BASE_URL = "https://www.googleapis.com/books/v1/"
}
