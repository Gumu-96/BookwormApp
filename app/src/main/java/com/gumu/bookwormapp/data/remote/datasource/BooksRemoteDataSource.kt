package com.gumu.bookwormapp.data.remote.datasource

import com.gumu.bookwormapp.data.remote.dto.BookSearchDto
import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.common.BookOrderByFilter
import com.gumu.bookwormapp.domain.common.BookPrintTypeFilter
import com.gumu.bookwormapp.domain.common.BookTypeFilter

interface BooksRemoteDataSource {
    suspend fun findBooks(
        query: String,
        page: Int,
        pageSize: Int,
        orderBy: BookOrderByFilter,
        printType: BookPrintTypeFilter,
        bookType: BookTypeFilter
    ): AppResult<BookSearchDto>
}
