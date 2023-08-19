package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : SignInUseCase {
    override fun invoke(email: String, password: String): Flow<AppResult<Unit>> {
        return repository.signIn(email, password)
    }
}
