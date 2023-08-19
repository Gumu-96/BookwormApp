package com.gumu.bookwormapp.domain.usecase.bookstats

import com.gumu.bookwormapp.domain.common.AppResult
import kotlinx.coroutines.flow.Flow

interface DeleteBookStatsUseCase {
    operator fun invoke(id: String): Flow<AppResult<Unit>>
}
