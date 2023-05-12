package com.gumu.bookwormapp.presentation.ui.search

import androidx.lifecycle.viewModelScope
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.BookOrderByFilter
import com.gumu.bookwormapp.domain.common.BookPrintTypeFilter
import com.gumu.bookwormapp.domain.common.BookTypeFilter
import com.gumu.bookwormapp.domain.common.onFailure
import com.gumu.bookwormapp.domain.common.onLoading
import com.gumu.bookwormapp.domain.common.onSuccess
import com.gumu.bookwormapp.domain.model.Book
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import com.gumu.bookwormapp.domain.repository.BooksRepository
import com.gumu.bookwormapp.presentation.ui.common.BaseViewModel
import com.gumu.bookwormapp.presentation.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val bookStatsRepository: BookStatsRepository
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
        _uiState.update { it.copy(
            displayBook = book,
            showBookDetails = true
        ) }
    }

    private fun onHideBookDetails() {
        _uiState.update { it.copy(showBookDetails = false) }
    }

    private fun onAddBookClick(book: Book) {
        viewModelScope.launch {
            bookStatsRepository.saveBookStats(BookStats(book)).collectLatest { result ->
                result.onLoading {
                    _uiState.update { it.copy(isAddingBook = true) }
                }.onSuccess {
                    _uiState.update { it.copy(isAddingBook = false) }
                    sendEvent(UiEvent.Error(R.string.generic_success_message))
                }.onFailure {
                    _uiState.update { it.copy(isAddingBook = false) }
                    sendEvent(UiEvent.Error(R.string.generic_error_message))
                }
            }
        }
    }

    override fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearchQueryChange -> onSearchQueryChange(event.searchQuery)
            is SearchEvent.OnAddBookClick -> onAddBookClick(event.book)
            is SearchEvent.OnBookClick -> onBookClick(event.book)
            SearchEvent.OnHideBookDetails -> onHideBookDetails()
            SearchEvent.OnClearQuery -> onClearQuery()
            SearchEvent.OnBackClick -> sendEvent(UiEvent.NavigateBack)
            SearchEvent.OnPerformSearch -> onPerformSearch()
            is SearchEvent.OnBookTypeClick -> onBookTypeClick(event.bookType)
            is SearchEvent.OnOrderByClick -> onOrderByClick(event.bookOrder)
            is SearchEvent.OnPrintTypeClick -> onPrintTypeClick(event.printType)
        }
    }
}
