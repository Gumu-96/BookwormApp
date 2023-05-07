package com.gumu.bookwormapp.presentation.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, ScreenEvent> : ViewModel() {
    protected val _uiState = MutableStateFlow(this.defaultState())
    abstract val uiState: StateFlow<State>

    private val _uiEventChannel = Channel<UiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    protected abstract fun defaultState(): State

    abstract fun onEvent(event: ScreenEvent)

    protected fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEventChannel.send(event)
        }
    }
}
