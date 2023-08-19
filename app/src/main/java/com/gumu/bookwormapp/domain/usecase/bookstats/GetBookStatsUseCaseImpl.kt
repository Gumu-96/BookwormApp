package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookStatsUseCaseImpl @Inject constructor(
    private val repository: BookStatsRepository
) : GetBookStatsUseCase {
    override fun invoke(id: String): Flow<AppResult<BookStats?>> {
        return repository.getBookStats(id)
    }
}
