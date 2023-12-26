package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.repository.BookStatsRepository

class GetBookStatsUseCase(
    private val repository: BookStatsRepository
) {
    suspend operator fun invoke(id: String): AppResult<BookStats?> {
        return repository.getBookStats(id)
    }
}
