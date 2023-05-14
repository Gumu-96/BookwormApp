package com.gumu.bookwormapp.domain.repository

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import kotlinx.coroutines.flow.Flow

interface BookStatsRepository {
    fun saveBookStats(bookStats: BookStats): Flow<AppResult<Unit>>
    fun updateBookStats(bookStatsId: String): Flow<AppResult<Unit>>
    fun getAllBookStats(status: ReadingStatus): Flow<PagingData<BookStats>>
    fun getBookStats(bookStatsId: String): Flow<AppResult<BookStats?>>
}
