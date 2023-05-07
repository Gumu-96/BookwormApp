package com.gumu.bookwormapp.presentation.ui.signup

import androidx.annotation.StringRes

data class SignUpState(
    val isLoading: Boolean = false,
    val firstname: String = "",
    @StringRes val firstnameError: Int? = null,
    val lastname: String = "",
    @StringRes val lastnameError: Int? = null,
    val email: String = "",
    @StringRes val emailError: Int? = null,
    val password: String = "",
    @StringRes val passwordError: Int? = null,
    val repeatedPassword: String = "",
    @StringRes val repeatedPasswordError: Int? = null
)
