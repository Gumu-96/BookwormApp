package com.gumu.bookwormapp.presentation.ui.signin

sealed class SignInEvent {
    data class OnEmailChange(val email: String) : SignInEvent()
    data class OnPasswordChange(val password: String) : SignInEvent()
    object OnSignInClick : SignInEvent()
    object OnSignUpClick : SignInEvent()
}
