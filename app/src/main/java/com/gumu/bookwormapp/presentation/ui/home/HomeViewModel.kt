package com.gumu.bookwormapp.presentation.ui.home

import com.google.firebase.auth.FirebaseAuth
import com.gumu.bookwormapp.presentation.navigation.Screen
import com.gumu.bookwormapp.presentation.ui.common.BaseViewModel
import com.gumu.bookwormapp.presentation.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel<HomeState, HomeEvent>() {
    override val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    override fun defaultState(): HomeState = HomeState()

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnBookStatsClick -> {}
            HomeEvent.OnAddBookClick -> sendEvent(UiEvent.NavigateTo(Screen.SearchScreen.route))
            HomeEvent.OnAccountClick -> {
                firebaseAuth.signOut()
                sendEvent(UiEvent.NavigateTo(Screen.SignInScreen.route, Screen.HomeScreen.route))
            }
        }
    }
}