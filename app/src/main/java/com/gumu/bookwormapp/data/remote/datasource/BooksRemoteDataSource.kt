package com.gumu.bookwormapp.data.remote.datasource

import com.gumu.bookwormapp.data.remote.dto.BookSearchDto
import com.gumu.bookwormapp.domain.common.AppResult

interface BooksRemoteDataSource {
    suspend fun findBooks(
        query: String,
        page: Int,
        pageSize: Int
    ): AppResult<BookSearchDto>
}
