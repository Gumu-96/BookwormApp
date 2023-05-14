package com.gumu.bookwormapp.data.remote

import com.gumu.bookwormapp.domain.common.AppError
import com.gumu.bookwormapp.domain.common.AppResult

object RemoteConstants {
    val UNEXPECTED_ERROR = AppResult.Failure(AppError(cause = Throwable("Unexpected response")))

    // Firebase
    const val USERS_COLLECTION = "users"
    const val BOOK_STATS_COLLECTION = "book_stats"
    const val BOOK_ID_FIELD = "bookId"
    const val USER_ID_FIELD = "userId"
    const val BOOK_STATUS_FIELD = "status"
    const val DEFAULT_STATS_PAGE_SIZE = 20

    // Google's Books API
    const val DEFAULT_PAGE_SIZE = 20
    const val MAX_PAGE_SIZE = 40 // Max allowed value to retrieve on a single request
    const val BOOKS_BASE_URL = "https://www.googleapis.com/books/v1/"
}
