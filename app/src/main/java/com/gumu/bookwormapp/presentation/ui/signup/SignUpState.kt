package com.gumu.bookwormapp.presentation.ui.signup

import androidx.annotation.StringRes

data class SignUpState(
    val isLoading: Boolean = false,
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val repeatedPassword: String = "",
    val errorState: SignUpErrorState = SignUpErrorState()
)

data class SignUpErrorState(
    @StringRes val firstnameError: Int? = null,
    @StringRes val lastnameError: Int? = null,
    @StringRes val emailError: Int? = null,
    @StringRes val passwordError: Int? = null,
    @StringRes val repeatedPasswordError: Int? = null
)
