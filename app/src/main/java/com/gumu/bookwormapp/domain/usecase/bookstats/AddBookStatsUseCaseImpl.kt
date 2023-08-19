package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddBookStatsUseCaseImpl @Inject constructor(
    private val repository: BookStatsRepository
) : AddBookStatsUseCase {
    override fun invoke(bookStats: BookStats): Flow<AppResult<Unit>> {
        return repository.saveBookStats(bookStats)
    }
}