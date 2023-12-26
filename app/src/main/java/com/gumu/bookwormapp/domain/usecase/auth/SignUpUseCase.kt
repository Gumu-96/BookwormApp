package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignUpUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<AppResult<String?>> {
        return repository.registerUser(email, password)
    }
}
