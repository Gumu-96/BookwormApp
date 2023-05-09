package com.gumu.bookwormapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gumu.bookwormapp.data.remote.RemoteConstants.DEFAULT_PAGE_SIZE
import com.gumu.bookwormapp.data.remote.datasource.BooksRemoteDataSource
import com.gumu.bookwormapp.data.util.toDomain
import com.gumu.bookwormapp.domain.common.AppResult
import com.gumu.bookwormapp.domain.model.Book

class BooksPagingSource(
    private val booksRemoteDataSource: BooksRemoteDataSource,
    private val query: String
) : PagingSource<Int, Book>() {
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val currentPage = params.key ?: 1

        return when (val result = booksRemoteDataSource.findBooks(query, currentPage, params.loadSize)) {
            is AppResult.Failure -> LoadResult.Error(result.error.cause)
            is AppResult.Success -> {
                val nextKey = if (result.data.items.isNullOrEmpty()) null
                    else currentPage.plus(params.loadSize / DEFAULT_PAGE_SIZE)

                LoadResult.Page(
                    data = result.data.items?.map { it.toDomain() } ?: emptyList(),
                    prevKey = null,
                    nextKey = nextKey
                )
            }
            else -> LoadResult.Error(Throwable("How did we get here?"))
        }
    }
}
