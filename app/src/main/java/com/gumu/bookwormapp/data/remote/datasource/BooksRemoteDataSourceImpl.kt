package com.gumu.bookwormapp.data.remote.datasource

import com.gumu.bookwormapp.data.remote.RemoteConstants.UNEXPECTED_ERROR
import com.gumu.bookwormapp.data.remote.api.BooksApi
import com.gumu.bookwormapp.data.remote.dto.BookSearchDto
import com.gumu.bookwormapp.data.util.DispatchersProvider
import com.gumu.bookwormapp.domain.common.AppError
import com.gumu.bookwormapp.domain.common.AppResult
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BooksRemoteDataSourceImpl @Inject constructor(
    private val booksApi: BooksApi,
    private val dispatchersProvider: DispatchersProvider
) : BooksRemoteDataSource {
    override suspend fun findBooks(
        query: String,
        page: Int,
        pageSize: Int
    ): AppResult<BookSearchDto> {
        return withContext(dispatchersProvider.io) {
            try {
                val response = booksApi.findBooks(query, page * pageSize, pageSize).execute()
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        AppResult.Success(data)
                    } ?: UNEXPECTED_ERROR
                } else {
                    AppResult.Failure(
                        AppError(
                            code = response.code(),
                            cause = Throwable(response.errorBody().toString())
                        )
                    )
                }
            } catch (e: IOException) {
                AppResult.Failure(AppError(cause = e))
            } catch (e: HttpException) {
                AppResult.Failure(AppError(cause = e))
            }
        }
    }
}
