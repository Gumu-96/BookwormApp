package com.gumu.bookwormapp.domain.usecase.bookstats

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBookStatsUseCase @Inject constructor(
    private val repository: BookStatsRepository
) {
    operator fun invoke(status: ReadingStatus): Flow<PagingData<BookStats>> {
        return repository.getAllBookStats(status)
    }
}
