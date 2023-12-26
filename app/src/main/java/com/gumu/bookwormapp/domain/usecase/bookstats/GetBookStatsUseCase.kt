package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.flow.Flow

class GetBookStatsUseCase(
    private val repository: BookStatsRepository
) {
    operator fun invoke(id: String): Flow<AppResult<BookStats?>> {
        return repository.getBookStats(id)
    }
}
