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
import kotlinx.coroutines.suspendCancellableCoroutine
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
                .addOnFailureListener { continuation.resume(AppResult.Failure(AppError(it))) }
        }

    override suspend fun registerUser(email: String, password: String): AppResult<String?> =
        suspendCancellableCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let { continuation.resume(AppResult.Success(it.uid)) }
                }
                .addOnFailureListener { continuation.resume(AppResult.Failure(AppError(it))) }
        }

    override suspend fun saveNewUserData(user: User): AppResult<Unit> =
        suspendCancellableCoroutine { continuation ->
            firestore.collection(RemoteConstants.USERS_COLLECTION)
                .document(user.id)
                .set(user.toDto())
                .addOnSuccessListener { continuation.resume(AppResult.Success(Unit)) }
                .addOnFailureListener { continuation.resume(AppResult.Failure(AppError(it))) }
        }

    override suspend fun signOut(): AppResult<Unit> = suspendCancellableCoroutine { continuation ->
        val listener = AuthStateListener {
            if (continuation.isActive) continuation.resume(AppResult.Success(Unit))
        }
        auth.addAuthStateListener(listener)
        auth.signOut()
        continuation.invokeOnCancellation { auth.removeAuthStateListener(listener) }
    }
}
