package com.gumu.bookwormapp.domain.usecase.auth

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface SaveNewUserDataUseCase {
    operator fun invoke(user: User): Flow<AppResult<Unit>>
}
