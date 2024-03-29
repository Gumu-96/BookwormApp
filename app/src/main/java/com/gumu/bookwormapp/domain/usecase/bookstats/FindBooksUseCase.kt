package com.gumu.bookwormapp.domain.usecase.bookstats

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.common.BookOrderByFilter
import com.gumu.bookwormapp.domain.common.BookPrintTypeFilter
import com.gumu.bookwormapp.domain.common.BookTypeFilter
import com.gumu.bookwormapp.domain.model.Book
import com.gumu.bookwormapp.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

class FindBooksUseCase(
    private val repository: BooksRepository
) {
    operator fun invoke(
        query: String,
        orderBy: BookOrderByFilter,
        printType: BookPrintTypeFilter,
        bookType: BookTypeFilter
    ): Flow<PagingData<Book>> {
        return repository.findBooks(
            query = query,
            orderBy = orderBy,
            printType = printType,
            bookType = bookType
        )
    }
}
