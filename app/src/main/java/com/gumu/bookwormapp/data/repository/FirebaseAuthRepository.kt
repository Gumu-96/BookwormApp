package com.gumu.bookwormapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.firestore.FirebaseFirestore
import com.gumu.bookwormapp.data.remote.RemoteConstants
import com.gumu.bookwormapp.data.remote.dto.toDto
import com.gumu.bookwormapp.domain.common.AppError
import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.User
import com.gumu.bookwormapp.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun checkUserSession(): Boolean = auth.currentUser != null

    override suspend fun signIn(email: String, password: String): AppResult<Unit> =
        suspendCancellableCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it.user?.let { continuation.resume(AppResult.Success(Unit)) }
                }
                .addOnFailureListener {
                    continuation.resume(AppResult.Failure(AppError(it)))
                }
        }

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

    override fun saveNewUserData(user: User): Flow<AppResult<Unit>> = callbackFlow {
        firestore.collection(RemoteConstants.USERS_COLLECTION)
            .document(user.id)
            .set(user.toDto())
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
