package com.gumu.bookwormapp.presentation.ui.bookstats

import androidx.lifecycle.viewModelScope
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.onFailure
import com.gumu.bookwormapp.domain.common.onSuccess
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import com.gumu.bookwormapp.domain.usecase.bookstats.DeleteBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.GetBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.UpdateBookStatsUseCase
import com.gumu.bookwormapp.presentation.ui.common.BaseViewModel
import com.gumu.bookwormapp.presentation.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookStatsViewModel @Inject constructor(
    private val getBookStatsUseCase: GetBookStatsUseCase,
    private val updateBookStatsUseCase: UpdateBookStatsUseCase,
    private val deleteBookStatsUseCase: DeleteBookStatsUseCase
) : BaseViewModel<BookStatsState, BookStatsEvent>() {
    private var initialStats: BookStats? = null

    override val uiState: StateFlow<BookStatsState> = _uiState.asStateFlow()

    override fun defaultState(): BookStatsState = BookStatsState()

    private fun checkForChanges() {
        _uiState.update { it.copy(
            hasChanges = initialStats?.rating != it.rating ||
                initialStats?.thoughts != it.thoughts ||
                initialStats?.status != it.status
        ) }
    }

    private fun onLoadStats(statsId: String?) {
        statsId?.let { id ->
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                getBookStatsUseCase(id).onSuccess { statsResult ->
                    statsResult?.let { stats ->
                        initialStats = stats
                        _uiState.update { it.copy(
                            book = stats.book,
                            rating = stats.rating,
                            thoughts = stats.thoughts,
                            status = stats.status,
                            isLoading = false
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
        } ?: run {
            _uiState.update { it.copy(isLoading = false) }
            sendEvent(UiEvent.ShowToast(R.string.stats_not_found_message))
        }
    }

    private fun onSetRating(rating: Int) {
        _uiState.update { it.copy(rating = rating) }
        checkForChanges()
    }

    private fun onThoughtsChange(thoughts: String) {
        _uiState.update { it.copy(thoughts = thoughts.ifBlank { null }) }
        checkForChanges()
    }

    private fun onStatusChange(status: ReadingStatus) {
        _uiState.update { it.copy(status = status) }
        checkForChanges()
    }

    private fun onConfirmDelete() {
        initialStats?.id?.let { id ->
            viewModelScope.launch {
                _uiState.update { it.copy(
                    showDeleteDialog = false,
                    isLoading = true
                ) }
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
        initialStats?.let { stats ->
            viewModelScope.launch {
                _uiState.update { it.copy(savingChanges = true) }
                val updatedStats = stats.copy(
                    rating = _uiState.value.rating,
                    thoughts = _uiState.value.thoughts,
                    status = _uiState.value.status
                )

                updateBookStatsUseCase(updatedStats).onSuccess {
                    initialStats = updatedStats
                    sendEvent(UiEvent.ShowToast(R.string.generic_success_message))
                    checkForChanges()
                    _uiState.update { it.copy(savingChanges = false) }
                }.onFailure {
                    sendEvent(UiEvent.ShowToast(R.string.generic_error_message))
                    _uiState.update { it.copy(savingChanges = false) }
                }
            }
        }
    }

    override fun onEvent(event: BookStatsEvent) {
        when (event) {
            is BookStatsEvent.OnLoadStats -> onLoadStats(event.statsId)
            BookStatsEvent.OnBackClick -> {
                if (_uiState.value.hasChanges) {
                    _uiState.update { it.copy(showLeaveDialog = true) }
                } else {
                    sendEvent(UiEvent.NavigateBack)
                }
            }
            BookStatsEvent.OnConfirmLeave -> {
                _uiState.update { it.copy(showLeaveDialog = false) }
                sendEvent(UiEvent.NavigateBack)
            }
            BookStatsEvent.OnSaveChangesClick -> onSaveChanges()
            BookStatsEvent.OnDeleteClick -> {
                _uiState.update { it.copy(showDeleteDialog = true) }
            }
            BookStatsEvent.OnConfirmDelete -> onConfirmDelete()
            BookStatsEvent.OnDismissDialog -> {
                _uiState.update { it.copy(
                    showDeleteDialog = false,
                    showLeaveDialog = false
                ) }
            }
            is BookStatsEvent.OnSetRating -> onSetRating(event.rating)
            is BookStatsEvent.OnThoughtsChange -> onThoughtsChange(event.thoughts)
            is BookStatsEvent.OnStatusChange -> onStatusChange(event.status)
        }
    }
}
