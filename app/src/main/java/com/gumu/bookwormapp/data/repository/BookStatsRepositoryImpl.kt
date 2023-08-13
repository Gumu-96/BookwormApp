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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookStatsRepositoryImpl @Inject constructor(
    private val bookStatsDataSource: BookStatsDataSource,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : BookStatsRepository {

    override fun saveBookStats(bookStats: BookStats): Flow<AppResult<Unit>> = flow {
        auth.currentUser?.uid?.let { userId ->
            val task = firestore
                .collection(RemoteConstants.USERS_COLLECTION)
                .document(userId)
                .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                .document(bookStats.book.id)
                .set(bookStats.toDto()).also { it.await() }

            if (task.isSuccessful) {
                emit(AppResult.Success(Unit))
            } else {
                emit(AppResult.Failure(AppError(task.exception ?: Throwable("Save data error"))))
            }
        } ?: emit(UNEXPECTED_ERROR)
    }.onStart { emit(AppResult.Loading) }

    override fun updateBookStats(bookStats: BookStats): Flow<AppResult<Unit>> = flow {
        auth.currentUser?.uid?.let { userId ->
            bookStats.id?.let { id ->
                val updateTask = firestore
                    .collection(RemoteConstants.USERS_COLLECTION)
                    .document(userId)
                    .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                    .document(id)
                    .update(bookStats.toUpdateMap())
                    .also { it.await() }

                if (updateTask.isSuccessful) {
                    emit(AppResult.Success(Unit))
                } else {
                    emit(AppResult.Failure(AppError(updateTask.exception ?: Throwable("Update error"))))
                }
            }
        } ?: emit(UNEXPECTED_ERROR)
    }.onStart { emit(AppResult.Loading) }

    override fun getAllBookStats(status: ReadingStatus): Flow<PagingData<BookStats>> {
        return Pager(
            config = PagingConfig(pageSize = RemoteConstants.DEFAULT_STATS_PAGE_SIZE),
            pagingSourceFactory = {
                BookStatsPagingSource(
                    bookStatsDataSource = bookStatsDataSource,
                    userId = auth.currentUser?.uid ?: "",
                    status = status
                )
            }
        ).flow
    }

    override fun getBookStats(bookStatsId: String): Flow<AppResult<BookStats?>> = flow {
        auth.currentUser?.uid?.let { userId ->
            val bookStatsTask = firestore
                .collection(RemoteConstants.USERS_COLLECTION)
                .document(userId)
                .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                .document(bookStatsId)
                .get()
                .also { it.await() }

            if (bookStatsTask.isSuccessful) {
                val docRef = bookStatsTask.result
                val bookStats = docRef?.toObject(BookStatsDto::class.java)?.toDomain(docRef.id)
                emit(AppResult.Success(bookStats))
            } else {
                emit(AppResult.Failure(AppError(bookStatsTask.exception ?: Throwable("Error getting data"))))
            }
        } ?: emit(UNEXPECTED_ERROR)
    }.onStart { emit(AppResult.Loading) }

    override fun deleteBookStats(bookStatsId: String): Flow<AppResult<Unit>> = flow {
        auth.currentUser?.uid?.let { userId ->
            val bookStatsTask = firestore
                .collection(RemoteConstants.USERS_COLLECTION)
                .document(userId)
                .collection(RemoteConstants.BOOK_STATS_COLLECTION)
                .document(bookStatsId)
                .delete()
                .also { it.await() }

            if (bookStatsTask.isSuccessful) {
                emit(AppResult.Success(Unit))
            } else {
                emit(AppResult.Failure(AppError(bookStatsTask.exception ?: Throwable("Error deleting data"))))
            }
        } ?: emit(UNEXPECTED_ERROR)
    }.onStart { emit(AppResult.Loading) }
}
