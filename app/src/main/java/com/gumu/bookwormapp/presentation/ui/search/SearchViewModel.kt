package com.gumu.bookwormapp.presentation.ui.search

import com.gumu.bookwormapp.domain.model.Book
import com.gumu.bookwormapp.domain.repository.BooksRepository
import com.gumu.bookwormapp.presentation.ui.common.BaseViewModel
import com.gumu.bookwormapp.presentation.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val booksRepository: BooksRepository
) : BaseViewModel<SearchState, SearchEvent>() {
    override val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    override fun defaultState(): SearchState = SearchState()

    private fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    private fun onPerformSearch() {
        if (_uiState.value.searchQuery.isNotBlank()) {
            _uiState.update { it.copy(
                books = booksRepository.findBooks(_uiState.value.searchQuery)
            ) }
        }
    }

    private fun onClearQuery() {
        _uiState.update { it.copy(searchQuery = "") }
    }

    private fun onBookClick(book: Book) {

    }

    override fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearchQueryChange -> onSearchQueryChange(event.searchQuery)
            is SearchEvent.OnBookClick -> onBookClick(event.book)
            SearchEvent.OnBackClick -> sendEvent(UiEvent.NavigateBack)
            SearchEvent.OnPerformSearch -> onPerformSearch()
            SearchEvent.OnClearQuery -> onClearQuery()
        }
    }
}
