package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookStatsUseCase @Inject constructor(
    private val repository: BookStatsRepository
) {
    operator fun invoke(id: String): Flow<AppResult<BookStats?>> {
        return repository.getBookStats(id)
    }
}
