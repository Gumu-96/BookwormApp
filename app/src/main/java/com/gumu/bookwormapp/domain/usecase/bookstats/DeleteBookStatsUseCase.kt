package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.repository.BookStatsRepository

class DeleteBookStatsUseCase(
    private val repository: BookStatsRepository
) {
    suspend operator fun invoke(id: String): AppResult<Unit> {
        return repository.deleteBookStats(id)
    }
}
