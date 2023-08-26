package com.gumu.bookwormapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gumu.bookwormapp.data.paging.BookStatsPagingSource
import com.gumu.bookwormapp.data.remote.RemoteConstants
import com.gumu.bookwormapp.data.remote.RemoteConstants.UNEXPECTED_ERROR
import com.gumu.bookwormapp.data.remote.datasource.BookStatsDataSource
import com.gumu.bookwormapp.data.remote.dto.BookStatsDto
import com.gumu.bookwormapp.data.remote.dto.toDomain
import com.gumu.bookwormapp.data.remote.dto.toDto
import com.gumu.bookwormapp.data.remote.dto.toUpdateMap
import com.gumu.bookwormapp.domain.common.AppError
import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookStatsRepositoryImpl @Inject constructor(
    private val bookStatsDataSource: BookStatsDataSource,
    private val firestore: FirebaseFirestore,
    auth: FirebaseAuth
) : BookStatsRepository {
    private val currentUserId = auth.currentUser?.uid

    override fun saveBookStats(bookStats: BookStats): Flow<AppResult<Unit>> = callbackFlow {
        currentUserId?.let { userId ->
            firestore
                .collection(RemoteConstants.USERS_COLLECTION)
                .document(userId)
                .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                .document(bookStats.book.id)
                .set(bookStats.toDto())
                .addOnSuccessListener {
                    trySend(AppResult.Success(Unit))
                }
                .addOnFailureListener {
                    trySend(AppResult.Failure(AppError(it)))
                }
                .await()
        } // ?: trySend(UNEXPECTED_ERROR) // This triggers even when the user is logged in
        awaitClose()
    }.onStart { emit(AppResult.Loading) }

    override fun updateBookStats(bookStats: BookStats): Flow<AppResult<Unit>> = callbackFlow {
        currentUserId?.let { userId ->
            bookStats.id?.let { id ->
                try {
                    firestore
                        .collection(RemoteConstants.USERS_COLLECTION)
                        .document(userId)
                        .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                        .document(id)
                        .update(bookStats.toUpdateMap())
                        .addOnSuccessListener {
                            trySend(AppResult.Success(Unit))
                        }
                        .addOnFailureListener {
                            trySend(AppResult.Failure(AppError(it)))
                        }
                        .await()
                } catch (ex: Exception) {
                    trySend(AppResult.Failure(AppError(ex)))
                }
            } ?: trySend(UNEXPECTED_ERROR)
        } // ?: trySend(UNEXPECTED_ERROR) // This triggers even when the user is logged in
        awaitClose()
    }.onStart { emit(AppResult.Loading) }

    override fun getAllBookStats(status: ReadingStatus): Flow<PagingData<BookStats>> {
        return Pager(
            config = PagingConfig(pageSize = RemoteConstants.DEFAULT_STATS_PAGE_SIZE),
            pagingSourceFactory = {
                BookStatsPagingSource(
                    bookStatsDataSource = bookStatsDataSource,
                    userId = currentUserId ?: "",
                    status = status
                )
            }
        ).flow
    }

    override fun getBookStats(bookStatsId: String): Flow<AppResult<BookStats?>> = callbackFlow {
        currentUserId?.let { userId ->
            firestore
                .collection(RemoteConstants.USERS_COLLECTION)
                .document(userId)
                .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                .document(bookStatsId)
                .get()
                .addOnSuccessListener {
                    val bookStats = it?.toObject(BookStatsDto::class.java)?.toDomain(it.id)
                    trySend(AppResult.Success(bookStats))
                }
                .addOnFailureListener {
                    trySend(AppResult.Failure(AppError(it)))
                }
                .await()
        } ?: trySend(UNEXPECTED_ERROR)
        awaitClose()
    }.onStart { emit(AppResult.Loading) }

    override fun deleteBookStats(bookStatsId: String): Flow<AppResult<Unit>> = callbackFlow {
        currentUserId?.let { userId ->
            firestore
                .collection(RemoteConstants.USERS_COLLECTION)
                .document(userId)
                .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                .document(bookStatsId)
                .delete()
                .addOnSuccessListener {
                    trySend(AppResult.Success(Unit))
                }
                .addOnFailureListener {
                    trySend(AppResult.Failure(AppError(it)))
                }
                .await()
        } // ?: trySend(UNEXPECTED_ERROR) // This triggers even when the user is logged in
        awaitClose()
    }.onStart { emit(AppResult.Loading) }
}
