package com.gumu.bookwormapp.domain.repository

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import kotlinx.coroutines.flow.Flow

interface BookStatsRepository {
    fun saveBookStats(bookStats: BookStats): Flow<AppResult<Unit>>
    fun updateBookStats(bookId: String): Flow<AppResult<Unit>>
    fun getAllBookStats(): Flow<PagingData<BookStats>>
    fun getBookStats(bookId: String): Flow<AppResult<BookStats?>>
}
