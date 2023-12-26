package com.gumu.bookwormapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gumu.bookwormapp.data.paging.BookStatsPagingSource
import com.gumu.bookwormapp.data.remote.RemoteConstants
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class BookStatsRepositoryImpl @Inject constructor(
    private val bookStatsDataSource: BookStatsDataSource,
    private val firestore: FirebaseFirestore,
    auth: FirebaseAuth
) : BookStatsRepository {
    private val currentUserId = auth.currentUser?.uid

    override suspend fun saveBookStats(bookStats: BookStats): AppResult<Unit> =
        suspendCancellableCoroutine { continuation ->
            currentUserId?.let { userId ->
                firestore
                    .collection(RemoteConstants.USERS_COLLECTION)
                    .document(userId)
                    .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                    .document(bookStats.book.id)
                    .set(bookStats.toDto())
                    .addOnSuccessListener { continuation.resume(AppResult.Success(Unit)) }
                    .addOnFailureListener { continuation.resume(AppResult.Failure(AppError(it))) }
            } // ?: continuation.resume(UNEXPECTED_ERROR) // This triggers even when the user is logged in
        }

    override suspend fun updateBookStats(bookStats: BookStats): AppResult<Unit> =
        suspendCancellableCoroutine { continuation ->
            currentUserId?.let { userId ->
                if (bookStats.id == null) {
                    continuation.resume(AppResult.Failure(AppError(Throwable("Invalid book id"))))
                }
                try {
                    firestore
                        .collection(RemoteConstants.USERS_COLLECTION)
                        .document(userId)
                        .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                        .document(bookStats.id!!)
                        .update(bookStats.toUpdateMap())
                        .addOnSuccessListener { continuation.resume(AppResult.Success(Unit)) }
                        .addOnFailureListener { continuation.resume(AppResult.Failure(AppError(it))) }
                } catch (ex: Exception) {
                    continuation.resume(AppResult.Failure(AppError(ex)))
                }
            }
        }

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

    override suspend fun getBookStats(bookStatsId: String): AppResult<BookStats?> =
        suspendCancellableCoroutine { continuation ->
            currentUserId?.let { userId ->
                firestore
                    .collection(RemoteConstants.USERS_COLLECTION)
                    .document(userId)
                    .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                    .document(bookStatsId)
                    .get()
                    .addOnSuccessListener {
                        val bookStats = it?.toObject(BookStatsDto::class.java)?.toDomain(it.id)
                        continuation.resume(AppResult.Success(bookStats))
                    }
                    .addOnFailureListener { continuation.resume(AppResult.Failure(AppError(it))) }
            } // ?: continuation.resume(UNEXPECTED_ERROR)
        }

    override suspend fun deleteBookStats(bookStatsId: String): AppResult<Unit> =
        suspendCancellableCoroutine { continuation ->
            currentUserId?.let { userId ->
                firestore
                    .collection(RemoteConstants.USERS_COLLECTION)
                    .document(userId)
                    .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                    .document(bookStatsId)
                    .delete()
                    .addOnSuccessListener { continuation.resume(AppResult.Success(Unit)) }
                    .addOnFailureListener { continuation.resume(AppResult.Failure(AppError(it))) }
            } // ?: continuation.resume(UNEXPECTED_ERROR) // This triggers even when the user is logged in
        }
}
