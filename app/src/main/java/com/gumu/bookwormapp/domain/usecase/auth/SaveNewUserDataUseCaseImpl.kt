package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.User
import com.gumu.bookwormapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveNewUserDataUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : SaveNewUserDataUseCase {
    override fun invoke(user: User): Flow<AppResult<Unit>> {
        return repository.saveNewUserData(user)
    }
}
