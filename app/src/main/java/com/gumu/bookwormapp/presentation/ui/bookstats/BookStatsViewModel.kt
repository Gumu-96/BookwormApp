package com.gumu.bookwormapp.presentation.ui.bookstats

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.onFailure
import com.gumu.bookwormapp.domain.common.onSuccess
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import com.gumu.bookwormapp.domain.usecase.bookstats.DeleteBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.GetBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.UpdateBookStatsUseCase
import com.gumu.bookwormapp.presentation.navigation.BookwormNavType
import com.gumu.bookwormapp.presentation.navigation.Screen
import com.gumu.bookwormapp.presentation.ui.common.BaseViewModel
import com.gumu.bookwormapp.presentation.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class BookStatsViewModel @Inject constructor(
    private val getBookStatsUseCase: GetBookStatsUseCase,
    private val updateBookStatsUseCase: UpdateBookStatsUseCase,
    private val deleteBookStatsUseCase: DeleteBookStatsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<BookStatsState, BookStatsIntent>() {

    private val args = savedStateHandle.toRoute<Screen.BookStatsScreen>(
        typeMap = mapOf(typeOf<BookStats>() to BookwormNavType.BookStatsType)
    )
    override val uiState: StateFlow<BookStatsState> = _uiState
        .onStart { loadStats() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), defaultState())

    override fun defaultState(): BookStatsState = BookStatsState()

    private fun loadStats() {
        if (uiState.value.initialStats != null) return // Stats already loaded
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getBookStatsUseCase(args.statsId).onSuccess { statsResult ->
                statsResult?.let { stats ->
                    _uiState.update { it.copy(
                        book = stats.book,
                        rating = stats.rating,
                        thoughts = stats.thoughts,
                        status = stats.status,
                        isLoading = false,
                        initialStats = stats
                    ) }
                } ?: run {
                    _uiState.update { it.copy(isLoading = false) }
                    sendEvent(UiEvent.ShowToast(R.string.stats_not_found_message))
                }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
                sendEvent(UiEvent.ShowToast(R.string.generic_error_message))
            }
        }
    }

    private fun onSetRating(rating: Int) {
        _uiState.update { it.copy(rating = rating) }
    }

    private fun onThoughtsChange(thoughts: String) {
        _uiState.update { it.copy(thoughts = thoughts.ifBlank { null }) }
    }

    private fun onStatusChange(status: ReadingStatus) {
        _uiState.update { it.copy(status = status) }
    }

    private fun onConfirmDelete() {
        uiState.value.initialStats?.id?.let { id ->
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        showDeleteDialog = false,
                        isLoading = true
                    )
                }
                deleteBookStatsUseCase.invoke(id).onSuccess {
                    sendEvent(UiEvent.ShowToast(R.string.generic_success_message))
                    sendEvent(UiEvent.NavigateBack)
                }.onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    sendEvent(UiEvent.ShowToast(R.string.generic_error_message))
                }
            }
        }
    }

    private fun onSaveChanges() {
        uiState.value.initialStats?.let { stats ->
            viewModelScope.launch {
                _uiState.update { it.copy(savingChanges = true) }
                val updatedStats = stats.copy(
                    rating = _uiState.value.rating,
                    thoughts = _uiState.value.thoughts,
                    status = _uiState.value.status
                )

                updateBookStatsUseCase(updatedStats).onSuccess {
                    sendEvent(UiEvent.ShowToast(R.string.generic_success_message))
                    _uiState.update {
                        it.copy(
                            savingChanges = false,
                            initialStats = updatedStats
                        )
                    }
                }.onFailure {
                    sendEvent(UiEvent.ShowToast(R.string.generic_error_message))
                    _uiState.update { it.copy(savingChanges = false) }
                }
            }
        }
    }

    override fun onIntent(intent: BookStatsIntent) {
        when (intent) {
            BookStatsIntent.OnBackClick -> {
                if (_uiState.value.hasChanges) {
                    _uiState.update { it.copy(showLeaveDialog = true) }
                } else {
                    sendEvent(UiEvent.NavigateBack)
                }
            }
            BookStatsIntent.OnConfirmLeave -> {
                _uiState.update { it.copy(showLeaveDialog = false) }
                sendEvent(UiEvent.NavigateBack)
            }
            BookStatsIntent.OnSaveChangesClick -> onSaveChanges()
            BookStatsIntent.OnDeleteClick -> {
                _uiState.update { it.copy(showDeleteDialog = true) }
            }
            BookStatsIntent.OnConfirmDelete -> onConfirmDelete()
            BookStatsIntent.OnDismissDialog -> {
                _uiState.update {
                    it.copy(
                        showDeleteDialog = false,
                        showLeaveDialog = false
                    )
                }
            }
            is BookStatsIntent.OnSetRating -> onSetRating(intent.rating)
            is BookStatsIntent.OnThoughtsChange -> onThoughtsChange(intent.thoughts)
            is BookStatsIntent.OnStatusChange -> onStatusChange(intent.status)
        }
    }
}
