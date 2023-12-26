package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.flow.Flow

class UpdateBookStatsUseCase(
    private val repository: BookStatsRepository
) {
    operator fun invoke(bookStats: BookStats): Flow<AppResult<Unit>> {
        return repository.updateBookStats(bookStats)
    }
}
