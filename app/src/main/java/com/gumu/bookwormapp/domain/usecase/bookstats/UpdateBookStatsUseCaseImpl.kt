package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.flow.Flow

class UpdateBookStatsUseCaseImpl(
    private val repository: BookStatsRepository
) : UpdateBookStatsUseCase {
    override fun invoke(bookStats: BookStats): Flow<AppResult<Unit>> {
        return repository.updateBookStats(bookStats)
    }
}
