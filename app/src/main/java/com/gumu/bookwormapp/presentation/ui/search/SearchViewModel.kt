package com.gumu.bookwormapp.presentation.ui.search

import com.gumu.bookwormapp.domain.common.BookOrderByFilter
import com.gumu.bookwormapp.domain.common.BookPrintTypeFilter
import com.gumu.bookwormapp.domain.common.BookTypeFilter
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

    private fun onOrderByClick(bookOrder: BookOrderByFilter) {
        _uiState.update { current -> current.copy(
            filterOptions = current.filterOptions.copy(bookOrder = bookOrder)
        ) }
    }

    private fun onPrintTypeClick(printType: BookPrintTypeFilter) {
        _uiState.update { current -> current.copy(
            filterOptions = current.filterOptions.copy(bookPrintType = printType)
        ) }
    }

    private fun onBookTypeClick(bookType: BookTypeFilter) {
        _uiState.update { current -> current.copy(
            filterOptions = current.filterOptions.copy(bookType = bookType)
        ) }
    }

    private fun onPerformSearch() {
        if (_uiState.value.searchQuery.isNotBlank()) {
            _uiState.update { it.copy(
                books = booksRepository.findBooks(
                    query = it.searchQuery,
                    orderBy = it.filterOptions.bookOrder,
                    printType = it.filterOptions.bookPrintType,
                    bookType = it.filterOptions.bookType
                )
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
            is SearchEvent.OnBookTypeClick -> onBookTypeClick(event.bookType)
            is SearchEvent.OnOrderByClick -> onOrderByClick(event.bookOrder)
            is SearchEvent.OnPrintTypeClick -> onPrintTypeClick(event.printType)
        }
    }
}
