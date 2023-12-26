package com.gumu.bookwormapp.domain.repository

import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.User

interface AuthRepository {
    fun checkUserSession(): Boolean
    suspend fun signIn(email: String, password: String): AppResult<Unit>
    suspend fun signOut(): AppResult<Unit>
    suspend fun registerUser(email: String, password: String): AppResult<String?>
    suspend fun saveNewUserData(user: User): AppResult<Unit>
}
