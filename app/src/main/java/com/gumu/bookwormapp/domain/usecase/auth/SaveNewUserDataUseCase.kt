package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.User
import com.gumu.bookwormapp.domain.repository.AuthRepository

class SaveNewUserDataUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: User): AppResult<Unit> {
        return repository.saveNewUserData(user)
    }
}
