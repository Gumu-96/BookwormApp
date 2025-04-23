package com.gumu.bookwormapp.presentation.ui.signup

sealed class SignUpIntent {
    data class OnFirstnameChange(val firstname: String) : SignUpIntent()
    data class OnLastnameChange(val lastname: String) : SignUpIntent()
    data class OnEmailChange(val email: String) : SignUpIntent()
    data class OnPasswordChange(val password: String) : SignUpIntent()
    data class OnRepeatedPasswordChange(val repeatedPassword: String) : SignUpIntent()
    data object OnRegisterClick : SignUpIntent()
    data object OnBackClick : SignUpIntent()
}
