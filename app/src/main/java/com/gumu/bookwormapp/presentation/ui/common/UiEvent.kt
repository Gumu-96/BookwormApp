package com.gumu.bookwormapp.presentation.ui.common

import androidx.annotation.StringRes

sealed class UiEvent {
    data class ShowToast(@StringRes val message: Int) : UiEvent()
    data class NavigateTo(val route: String, val popUpTo: String? = null) : UiEvent()
    object NavigateBack : UiEvent()
}
