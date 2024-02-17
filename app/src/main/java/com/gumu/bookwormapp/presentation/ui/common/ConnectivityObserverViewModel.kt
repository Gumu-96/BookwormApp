package com.gumu.bookwormapp.presentation.ui.common

import androidx.lifecycle.viewModelScope
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.presentation.util.ConnectivityObserver
import com.gumu.bookwormapp.presentation.util.ConnectivityObserver.ConnectivityStatus
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

abstract class ConnectivityObserverViewModel<State, ScreenEvent>(
    private val connectivityObserver: ConnectivityObserver
) : BaseViewModel<State, ScreenEvent>() {
    init {
        observeConnectivityStatus()
    }

    private fun observeConnectivityStatus() {
        viewModelScope.launch {
            connectivityObserver.observeConnectivity().drop(1).collectLatest {
                when (it) {
                    ConnectivityStatus.LOST,
                    ConnectivityStatus.LOOSING,
                    ConnectivityStatus.UNAVAILABLE -> onInternetNotAvailable()
                    else -> onInternetAvailable()
                }
            }
        }
    }

    protected open fun onInternetNotAvailable() {
        sendEvent(UiEvent.ShowToast(R.string.internet_not_available_msg))
    }

    protected open fun onInternetAvailable() {
        sendEvent(UiEvent.ShowToast(R.string.internet_available_msg))
    }
}
