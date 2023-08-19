package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import kotlinx.coroutines.flow.Flow

interface UpdateBookStatsUseCase {
    operator fun invoke(bookStats: BookStats): Flow<AppResult<Unit>>
}
