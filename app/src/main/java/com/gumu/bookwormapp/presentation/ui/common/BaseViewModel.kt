package com.gumu.bookwormapp.presentation.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, I> : ViewModel() {
    protected val _uiState = MutableStateFlow(this.defaultState())
    abstract val uiState: StateFlow<S>

    private val _sideEffectsChannel = Channel<UiEvent>()
    val sideEffects = _sideEffectsChannel.receiveAsFlow()

    protected abstract fun defaultState(): S

    abstract fun onIntent(intent: I)

    protected fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _sideEffectsChannel.send(event)
        }
    }
}
