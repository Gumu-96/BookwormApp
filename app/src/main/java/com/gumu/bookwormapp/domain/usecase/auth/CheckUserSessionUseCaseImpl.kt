package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.repository.AuthRepository

class CheckUserSessionUseCaseImpl(
    private val repository: AuthRepository
) : CheckUserSessionUseCase {
    override fun invoke(): Boolean {
        return repository.checkUserSession()
    }
}
