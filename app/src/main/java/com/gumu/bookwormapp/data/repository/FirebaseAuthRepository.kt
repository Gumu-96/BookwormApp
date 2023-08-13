package com.gumu.bookwormapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.firestore.FirebaseFirestore
import com.gumu.bookwormapp.data.remote.RemoteConstants
import com.gumu.bookwormapp.data.remote.dto.UserDto
import com.gumu.bookwormapp.domain.common.AppError
import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun checkUserSession(): Boolean = auth.currentUser != null

    override fun signIn(email: String, password: String): Flow<AppResult<Unit>> {
        return flow {
            val task = auth.signInWithEmailAndPassword(email, password).also { it.await() }

            if (task.isSuccessful) {
                task.result.user?.let { emit(AppResult.Success(Unit)) }
            } else {
                emit(AppResult.Failure(AppError(task.exception ?: Throwable("Auth error"))))
            }
        }.onStart { emit(AppResult.Loading) }
    }

    override fun registerUser(email: String, password: String): Flow<AppResult<String?>> {
        return flow {
            val task = auth.createUserWithEmailAndPassword(email, password).also { it.await() }

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
            val task = firestore
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

    override fun signOut(): Flow<AppResult<Unit>> {
        return callbackFlow {
            val listener = AuthStateListener { trySend(AppResult.Success(Unit)) }
            auth.addAuthStateListener(listener)
            auth.signOut()
            awaitClose { auth.removeAuthStateListener(listener) }
        }
    }
}
