package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.repository.AuthRepository

class CheckUserSessionUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return repository.checkUserSession()
    }
}
