package com.gumu.bookwormapp.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.gumu.bookwormapp.domain.common.onSuccess
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import com.gumu.bookwormapp.domain.repository.AuthRepository
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import com.gumu.bookwormapp.presentation.navigation.Screen
import com.gumu.bookwormapp.presentation.ui.common.BaseViewModel
import com.gumu.bookwormapp.presentation.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    bookStatsRepository: BookStatsRepository,
    private val authRepository: AuthRepository
) : BaseViewModel<HomeState, HomeEvent>() {
    override val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    /*
    If cached in viewModel scope, then they won't reload when navigating to different screens
    but won't reflect any changes made after loaded for the first time
    Currently the firestore library doesn't support paging with live updates (a similar behavior of Room with flows)
     */
    val onQueueBooks = bookStatsRepository.getAllBookStats(ReadingStatus.ON_QUEUE) // .cachedIn(viewModelScope)
    val readingBooks = bookStatsRepository.getAllBookStats(ReadingStatus.READING) // .cachedIn(viewModelScope)
    val readBooks = bookStatsRepository.getAllBookStats(ReadingStatus.READ) // .cachedIn(viewModelScope)

    override fun defaultState(): HomeState = HomeState()

    private fun onBookStatsClick(bookStats: BookStats) {
        bookStats.id?.let {
            sendEvent(UiEvent.NavigateTo(Screen.BookStatsScreen.withArgs(it)))
        }
    }

    private fun onSignOutClick() {
        viewModelScope.launch {
            authRepository.signOut().collectLatest {
                it.onSuccess {
                    sendEvent(UiEvent.NavigateTo(Screen.SignInScreen.route, Screen.HomeScreen.route))
                }
            }
        }
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnBookStatsClick -> onBookStatsClick(event.bookStats)
            HomeEvent.OnAddBookClick -> sendEvent(UiEvent.NavigateTo(Screen.SearchScreen.route))
            HomeEvent.OnAccountClick -> onSignOutClick()
        }
    }
}
