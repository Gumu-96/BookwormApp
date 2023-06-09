package com.gumu.bookwormapp.presentation.ui.search

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.common.BookOrderByFilter
import com.gumu.bookwormapp.domain.common.BookPrintTypeFilter
import com.gumu.bookwormapp.domain.common.BookTypeFilter
import com.gumu.bookwormapp.domain.model.Book
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val books: Flow<PagingData<Book>>? = null,
    val filterOptions: SearchFilterOptions = SearchFilterOptions(),
    val showBookDetails: Boolean = false,
    val displayBook: Book? = null,
    val isAddingBook: Boolean = false
)

data class SearchFilterOptions(
    val bookOrder: BookOrderByFilter = BookOrderByFilter.RELEVANCE,
    val bookPrintType: BookPrintTypeFilter = BookPrintTypeFilter.ALL,
    val bookType: BookTypeFilter = BookTypeFilter.ALL
)
