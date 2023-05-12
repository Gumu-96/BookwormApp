package com.gumu.bookwormapp.data.repository

import androidx.paging.PagingData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gumu.bookwormapp.data.remote.RemoteConstants
import com.gumu.bookwormapp.data.remote.dto.BookStatsDto
import com.gumu.bookwormapp.data.remote.dto.toDomain
import com.gumu.bookwormapp.data.remote.dto.toDto
import com.gumu.bookwormapp.domain.common.AppError
import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookStatsRepositoryImpl @Inject constructor() : BookStatsRepository {
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    override fun saveBookStats(bookStats: BookStats): Flow<AppResult<Unit>> = flow {
        val task = firestore
            .collection(RemoteConstants.BOOK_STATS_COLLECTION)
            .add(bookStats.toDto(auth.currentUser?.uid ?: "")).also { it.await() }

        if (task.isSuccessful) emit(AppResult.Success(Unit))
        else emit(AppResult.Failure(AppError(task.exception ?: Throwable("Save data error"))))
    }.onStart { emit(AppResult .Loading) }

    override fun updateBookStats(bookId: String): Flow<AppResult<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getAllBookStats(): Flow<PagingData<BookStats>> {
        TODO("Not yet implemented")
    }

    override fun getBookStats(bookId: String): Flow<AppResult<BookStats?>> = flow {
        val bookStatsTask = firestore
            .collection(RemoteConstants.BOOK_STATS_COLLECTION)
            .whereEqualTo(RemoteConstants.USER_ID_FIELD, auth.currentUser?.uid)
            .whereEqualTo(RemoteConstants.BOOK_ID_FIELD, bookId)
            .limit(1)
            .get()
            .also { it.await() }

        if (bookStatsTask.isSuccessful) {
            val statsDto = bookStatsTask.result.documents.first().toObject(BookStatsDto::class.java)
            emit(AppResult.Success(statsDto?.toDomain()))
        } else {
            emit(AppResult.Failure(AppError(bookStatsTask.exception ?: Throwable("Error getting data"))))
        }
    }
}
