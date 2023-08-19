package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteBookStatsUseCaseImpl @Inject constructor(
    private val repository: BookStatsRepository
) : DeleteBookStatsUseCase {
    override fun invoke(id: String): Flow<AppResult<Unit>> {
        return repository.deleteBookStats(id)
    }
}
