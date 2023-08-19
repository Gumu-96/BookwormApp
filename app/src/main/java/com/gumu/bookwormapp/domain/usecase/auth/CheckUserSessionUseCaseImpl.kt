package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.repository.AuthRepository
import javax.inject.Inject

class CheckUserSessionUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : CheckUserSessionUseCase {
    override fun invoke(): Boolean {
        return repository.checkUserSession()
    }
}
