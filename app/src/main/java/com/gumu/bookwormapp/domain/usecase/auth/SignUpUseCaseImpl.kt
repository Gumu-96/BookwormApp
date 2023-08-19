package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : SignUpUseCase {
    override fun invoke(email: String, password: String): Flow<AppResult<String?>> {
        return repository.registerUser(email, password)
    }
}
