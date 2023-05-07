package com.gumu.bookwormapp.presentation.ui.signin

import androidx.annotation.StringRes

data class SignInState(
    val isLoading: Boolean = false,
    val email: String = "",
    @StringRes val emailError: Int? = null,
    val password: String = "",
    @StringRes val passwordError: Int? = null
)
