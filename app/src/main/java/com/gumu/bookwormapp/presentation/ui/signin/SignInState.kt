package com.gumu.bookwormapp.presentation.ui.signin

import androidx.annotation.StringRes

data class SignInState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val errorState: SignInErrorState = SignInErrorState()
)

data class SignInErrorState(
    @StringRes val emailError: Int? = null,
    @StringRes val passwordError: Int? = null
)
