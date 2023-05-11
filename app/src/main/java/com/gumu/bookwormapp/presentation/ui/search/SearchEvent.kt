package com.gumu.bookwormapp.presentation.ui.search

import com.gumu.bookwormapp.domain.common.BookOrderByFilter
import com.gumu.bookwormapp.domain.common.BookPrintTypeFilter
import com.gumu.bookwormapp.domain.common.BookTypeFilter
import com.gumu.bookwormapp.domain.model.Book

sealed class SearchEvent {
    object OnBackClick : SearchEvent()
    object OnPerformSearch : SearchEvent()
    object OnClearQuery : SearchEvent()
    data class OnSearchQueryChange(val searchQuery: String) : SearchEvent()
    data class OnBookClick(val book: Book) : SearchEvent()
    data class OnOrderByClick(val bookOrder: BookOrderByFilter) : SearchEvent()
    data class OnPrintTypeClick(val printType: BookPrintTypeFilter) : SearchEvent()
    data class OnBookTypeClick(val bookType: BookTypeFilter) : SearchEvent()
}
