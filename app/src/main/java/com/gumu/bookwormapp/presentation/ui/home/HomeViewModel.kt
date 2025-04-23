package com.gumu.bookwormapp.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.gumu.bookwormapp.domain.common.onSuccess
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import com.gumu.bookwormapp.domain.usecase.auth.SignOutUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.GetAllBookStatsUseCase
import com.gumu.bookwormapp.presentation.navigation.Screen
import com.gumu.bookwormapp.presentation.ui.common.BaseViewModel
import com.gumu.bookwormapp.presentation.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllBookStatsUseCase: GetAllBookStatsUseCase,
    private val signOutUseCase: SignOutUseCase
) : BaseViewModel<HomeState, HomeEvent>() {
    override val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    /*
    If cached in viewModel scope, then they won't reload when navigating to different screens
    but won't reflect any changes made after loaded for the first time
    Currently the firestore library doesn't support paging with live updates (a similar behavior of Room with flows)
     */
    val onQueueBooks = getAllBookStatsUseCase.invoke(ReadingStatus.ON_QUEUE) // .cachedIn(viewModelScope)
    val readingBooks = getAllBookStatsUseCase.invoke(ReadingStatus.READING) // .cachedIn(viewModelScope)
    val readBooks = getAllBookStatsUseCase.invoke(ReadingStatus.READ) // .cachedIn(viewModelScope)

    override fun defaultState(): HomeState = HomeState()

    private fun onBookStatsClick(bookStats: BookStats) {
        bookStats.id?.let {
            sendEvent(UiEvent.Navigate(Screen.BookStatsScreen(it)))
        }
    }

    private fun onSignOutClick() {
        viewModelScope.launch {
            signOutUseCase().onSuccess {
                sendEvent(UiEvent.Navigate(Screen.SignInScreen, Screen.HomeScreen))
            }
        }
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnBookStatsClick -> onBookStatsClick(event.bookStats)
            HomeEvent.OnAddBookClick -> sendEvent(UiEvent.Navigate(Screen.SearchScreen))
            HomeEvent.OnAccountClick -> onSignOutClick()
        }
    }
}
