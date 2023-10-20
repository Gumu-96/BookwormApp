package com.gumu.bookwormapp.presentation.ui.signup

import com.gumu.bookwormapp.domain.common.UiText

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
    val firstnameError: UiText? = null,
    val lastnameError: UiText? = null,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val repeatedPasswordError: UiText? = null
)
