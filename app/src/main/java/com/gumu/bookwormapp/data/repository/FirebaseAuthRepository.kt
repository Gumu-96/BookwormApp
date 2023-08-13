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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun checkUserSession(): Boolean = auth.currentUser != null

    override fun signIn(email: String, password: String): Flow<AppResult<Unit>> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.let { trySend(AppResult.Success(Unit)) }
            }
            .addOnFailureListener {
                trySend(AppResult.Failure(AppError(it)))
            }
            .await()
        awaitClose()
    }.onStart { emit(AppResult.Loading) }

    override fun registerUser(email: String, password: String): Flow<AppResult<String?>> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let { trySend(AppResult.Success(it.uid)) }
            }
            .addOnFailureListener {
                trySend(AppResult.Failure(AppError(it)))
            }
            .await()
        awaitClose()
    }.onStart { emit(AppResult.Loading) }

    override fun saveNewUserData(
        userId: String,
        firstname: String,
        lastname: String
    ): Flow<AppResult<Unit>> = callbackFlow {
        firestore.collection(RemoteConstants.USERS_COLLECTION)
            .document(userId)
            .set(UserDto(firstname, lastname))
            .addOnSuccessListener {
                trySend(AppResult.Success(Unit))
            }
            .addOnFailureListener {
                trySend(AppResult.Failure(AppError(it)))
            }
            .await()
        awaitClose()
    }.onStart { emit(AppResult.Loading) }

    override fun signOut(): Flow<AppResult<Unit>> = callbackFlow {
        val listener = AuthStateListener { trySend(AppResult.Success(Unit)) }
        auth.addAuthStateListener(listener)
        auth.signOut()
        awaitClose { auth.removeAuthStateListener(listener) }
    }
}
