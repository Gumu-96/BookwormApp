package com.gumu.bookwormapp.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gumu.bookwormapp.data.remote.RemoteConstants
import com.gumu.bookwormapp.data.remote.dto.UserDto
import com.gumu.bookwormapp.domain.common.AppError
import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor() : AuthRepository {
    private val firebaseAuth = Firebase.auth
    private val firebaseFirestore = Firebase.firestore

    override fun checkUserSession(): Boolean = firebaseAuth.currentUser != null

    override fun signIn(email: String, password: String): Flow<AppResult<Unit>> {
        return flow {
            val task = firebaseAuth.signInWithEmailAndPassword(email, password).also { it.await() }

            if (task.isSuccessful) {
                task.result.user?.let { emit(AppResult.Success(Unit)) }
            } else {
                emit(AppResult.Failure(AppError(task.exception ?: Throwable("Auth error"))))
            }
        }.onStart { emit(AppResult.Loading) }
    }

    override fun registerUser(email: String, password: String): Flow<AppResult<String?>> {
        return flow {
            val task = firebaseAuth.createUserWithEmailAndPassword(email, password).also { it.await() }

            if (task.isSuccessful) {
                task.result.user?.let { emit(AppResult.Success(it.uid)) }
            } else {
                emit(AppResult.Failure(AppError(task.exception ?: Throwable("Registration error"))))
            }
        }.onStart { emit(AppResult.Loading) }
    }

    override fun saveNewUserData(
        userId: String,
        firstname: String,
        lastname: String
    ): Flow<AppResult<Unit>> {
        return flow {
            val task = firebaseFirestore
                .collection(RemoteConstants.USERS_COLLECTION)
                .document(userId)
                .set(UserDto(firstname, lastname)).also { it.await() }
            if (task.isSuccessful) {
                emit(AppResult.Success(Unit))
            } else {
                emit(AppResult.Failure(AppError(task.exception ?: Throwable("Save data error"))))
            }
        }.onStart { emit(AppResult.Loading) }
    }
}
