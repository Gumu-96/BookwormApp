package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.User
import com.gumu.bookwormapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class SaveNewUserDataUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(user: User): Flow<AppResult<Unit>> {
        return repository.saveNewUserData(user)
    }
}
