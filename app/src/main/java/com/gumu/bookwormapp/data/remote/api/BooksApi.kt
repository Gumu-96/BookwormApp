package com.gumu.bookwormapp.data.remote.api

import com.gumu.bookwormapp.data.remote.dto.BookSearchDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("volumes")
    fun findBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") pageSize: Int,
        @Query("orderBy") orderBy: String,
        @Query("printType") printType: String,
        @Query("filter") filter: String?
    ): Call<BookSearchDto>
}
