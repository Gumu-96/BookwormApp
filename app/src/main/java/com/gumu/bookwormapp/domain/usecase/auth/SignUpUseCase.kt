package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.common.AppResult
import kotlinx.coroutines.flow.Flow

interface SignUpUseCase {
    operator fun invoke(email: String, password: String): Flow<AppResult<String?>>
}
