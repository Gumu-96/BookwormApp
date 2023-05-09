package com.gumu.bookwormapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gumu.bookwormapp.data.paging.BooksPagingSource
import com.gumu.bookwormapp.data.remote.RemoteConstants
import com.gumu.bookwormapp.data.remote.datasource.BooksRemoteDataSource
import com.gumu.bookwormapp.domain.model.Book
import com.gumu.bookwormapp.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val booksRemoteDataSource: BooksRemoteDataSource
) : BooksRepository {
    override fun findBooks(query: String): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(
                pageSize = RemoteConstants.DEFAULT_PAGE_SIZE,
                initialLoadSize = RemoteConstants.MAX_PAGE_SIZE
            ),
            pagingSourceFactory = { BooksPagingSource(
                booksRemoteDataSource = booksRemoteDataSource,
                query = query
            ) }
        ).flow
    }
}
