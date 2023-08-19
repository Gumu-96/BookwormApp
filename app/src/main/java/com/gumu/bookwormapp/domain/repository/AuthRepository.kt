package com.gumu.bookwormapp.domain.repository

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun checkUserSession(): Boolean
    fun signIn(email: String, password: String): Flow<AppResult<Unit>>
    fun signOut(): Flow<AppResult<Unit>>
    fun registerUser(email: String, password: String): Flow<AppResult<String?>>
    fun saveNewUserData(user: User): Flow<AppResult<Unit>>
}
