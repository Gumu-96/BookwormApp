package com.gumu.bookwormapp.presentation.ui.search

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.model.Book
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val books: Flow<PagingData<Book>>? = null
)
