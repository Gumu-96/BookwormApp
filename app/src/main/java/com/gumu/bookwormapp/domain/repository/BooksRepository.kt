package com.gumu.bookwormapp.domain.repository

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun findBooks(query: String): Flow<PagingData<Book>>
}
