package com.gumu.bookwormapp.presentation.util

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun isConnected(): Boolean

    fun observe(): Flow<Boolean>
}
