package com.gumu.bookwormapp.presentation.util

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observeConnectivity(): Flow<ConnectivityStatus>

    enum class ConnectivityStatus {
        AVAILABLE, UNAVAILABLE, LOOSING, LOST
    }
}
