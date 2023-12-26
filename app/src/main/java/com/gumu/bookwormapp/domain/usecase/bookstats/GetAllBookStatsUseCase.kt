package com.gumu.bookwormapp.domain.usecase.bookstats

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.flow.Flow

class GetAllBookStatsUseCase(
    private val repository: BookStatsRepository
) {
    operator fun invoke(status: ReadingStatus): Flow<PagingData<BookStats>> {
        return repository.getAllBookStats(status)
    }
}
