package com.gumu.bookwormapp.presentation.ui.common

import androidx.annotation.StringRes
import com.gumu.bookwormapp.presentation.navigation.Screen

sealed class UiEvent {
    data class ShowToast(@StringRes val message: Int) : UiEvent()
    data class Navigate<T : Screen>(val destination: T, val popUpTo: T? = null) : UiEvent()
    data object NavigateBack : UiEvent()
}
