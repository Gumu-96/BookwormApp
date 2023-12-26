package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignOutUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<AppResult<Unit>> {
        return repository.signOut()
    }
}
