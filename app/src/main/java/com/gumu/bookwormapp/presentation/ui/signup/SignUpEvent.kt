package com.gumu.bookwormapp.presentation.ui.signup

sealed class SignUpEvent {
    data class OnFirstnameChange(val firstname: String) : SignUpEvent()
    data class OnLastnameChange(val lastname: String) : SignUpEvent()
    data class OnEmailChange(val email: String) : SignUpEvent()
    data class OnPasswordChange(val password: String) : SignUpEvent()
    data class OnRepeatedPasswordChange(val repeatedPassword: String) : SignUpEvent()
    object OnRegisterClick : SignUpEvent()
    object OnBackClick : SignUpEvent()
}
