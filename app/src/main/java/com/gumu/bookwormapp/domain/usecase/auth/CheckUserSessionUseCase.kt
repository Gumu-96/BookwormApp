package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.repository.AuthRepository
import javax.inject.Inject

class CheckUserSessionUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return repository.checkUserSession()
    }
}
