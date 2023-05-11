package com.gumu.bookwormapp.domain.repository

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.common.BookOrderByFilter
import com.gumu.bookwormapp.domain.common.BookPrintTypeFilter
import com.gumu.bookwormapp.domain.common.BookTypeFilter
import com.gumu.bookwormapp.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun findBooks(
        query: String,
        orderBy: BookOrderByFilter,
        printType: BookPrintTypeFilter,
        bookType: BookTypeFilter
    ): Flow<PagingData<Book>>
}
