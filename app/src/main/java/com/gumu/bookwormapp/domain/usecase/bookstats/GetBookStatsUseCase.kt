package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import kotlinx.coroutines.flow.Flow

interface GetBookStatsUseCase {
    operator fun invoke(id: String): Flow<AppResult<BookStats?>>
}
