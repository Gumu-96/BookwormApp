package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignInUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<AppResult<Unit>> {
        return repository.signIn(email, password)
    }
}
