package com.gumu.bookwormapp.presentation.ui.search

import com.gumu.bookwormapp.domain.model.Book

sealed class SearchEvent {
    object OnBackClick : SearchEvent()
    object OnPerformSearch : SearchEvent()
    object OnClearQuery : SearchEvent()
    data class OnSearchQueryChange(val searchQuery: String) : SearchEvent()
    data class OnBookClick(val book: Book) : SearchEvent()
}
