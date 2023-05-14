package com.gumu.bookwormapp.presentation.ui.home

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gumu.bookwormapp.domain.model.ReadingStatus
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import com.gumu.bookwormapp.presentation.navigation.Screen
import com.gumu.bookwormapp.presentation.ui.common.BaseViewModel
import com.gumu.bookwormapp.presentation.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookStatsRepository: BookStatsRepository
) : BaseViewModel<HomeState, HomeEvent>() {
    override val uiState: StateFlow<HomeState> = _uiState.asStateFlow()
    val onQueueBooks = bookStatsRepository.getAllBookStats(ReadingStatus.ON_QUEUE)
    val readingBooks = bookStatsRepository.getAllBookStats(ReadingStatus.READING)

    override fun defaultState(): HomeState = HomeState()

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnBookStatsClick -> {}
            HomeEvent.OnAddBookClick -> sendEvent(UiEvent.NavigateTo(Screen.SearchScreen.route))
            HomeEvent.OnAccountClick -> {
                Firebase.auth.signOut()
                sendEvent(UiEvent.NavigateTo(Screen.SignInScreen.route, Screen.HomeScreen.route))
            }
        }
    }
}
