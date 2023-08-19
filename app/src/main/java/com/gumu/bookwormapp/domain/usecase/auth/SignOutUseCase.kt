package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.common.AppResult
import kotlinx.coroutines.flow.Flow

interface SignOutUseCase {
    operator fun invoke(): Flow<AppResult<Unit>>
}
