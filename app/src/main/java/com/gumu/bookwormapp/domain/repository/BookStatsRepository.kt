package com.gumu.bookwormapp.domain.repository

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import kotlinx.coroutines.flow.Flow

interface BookStatsRepository {
    suspend fun saveBookStats(bookStats: BookStats): AppResult<Unit>
    suspend fun updateBookStats(bookStats: BookStats): AppResult<Unit>
    fun getAllBookStats(status: ReadingStatus): Flow<PagingData<BookStats>>
    suspend fun getBookStats(bookStatsId: String): AppResult<BookStats?>
    suspend fun deleteBookStats(bookStatsId: String): AppResult<Unit>
}
