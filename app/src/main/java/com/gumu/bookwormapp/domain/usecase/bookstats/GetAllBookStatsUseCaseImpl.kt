package com.gumu.bookwormapp.domain.usecase.bookstats

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBookStatsUseCaseImpl @Inject constructor(
    private val repository: BookStatsRepository
) : GetAllBookStatsUseCase {
    override fun invoke(status: ReadingStatus): Flow<PagingData<BookStats>> {
        return repository.getAllBookStats(status)
    }
}
