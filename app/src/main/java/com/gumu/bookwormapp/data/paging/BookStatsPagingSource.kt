package com.gumu.bookwormapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.DocumentSnapshot
import com.gumu.bookwormapp.data.remote.datasource.BookStatsDataSource
import com.gumu.bookwormapp.data.remote.dto.BookStatsDto
import com.gumu.bookwormapp.data.remote.dto.toDomain
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import kotlinx.coroutines.tasks.await

class BookStatsPagingSource(
    private val bookStatsDataSource: BookStatsDataSource,
    private val userId: String,
    private val status: ReadingStatus
) : PagingSource<DocumentSnapshot, BookStats>() {
    override fun getRefreshKey(state: PagingState<DocumentSnapshot, BookStats>): DocumentSnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<DocumentSnapshot>): LoadResult<DocumentSnapshot, BookStats> {
        val startAtDocument = params.key
        val queryTask = bookStatsDataSource.getBookStatsQuery(
            userId = userId,
            status = status,
            pageSize = params.loadSize,
            startAtDocument = startAtDocument
        ).get().also { it.await() }

        return if (queryTask.isSuccessful) {
            val docs = queryTask.result.documents
            val nextKey = if (docs.isEmpty() || docs.size < params.loadSize) null else docs.last()
            LoadResult.Page(
                data = docs.mapNotNull { it.toObject(BookStatsDto::class.java) }.map { it.toDomain() },
                prevKey = null,
                nextKey = nextKey
            )
        } else {
            LoadResult.Error(queryTask.exception ?: Throwable("Error in pagination"))
        }
    }
}
