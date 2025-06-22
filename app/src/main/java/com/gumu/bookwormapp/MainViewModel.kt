package com.gumu.bookwormapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumu.bookwormapp.presentation.util.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    connectivityObserver: ConnectivityObserver
) : ViewModel() {
    var isConnected by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            connectivityObserver.observe().collectLatest {
                isConnected = it
            }
        }
    }
}
