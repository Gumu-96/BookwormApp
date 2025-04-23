package com.gumu.bookwormapp.presentation.ui.signin

sealed class SignInIntent {
    data class OnEmailChange(val email: String) : SignInIntent()
    data class OnPasswordChange(val password: String) : SignInIntent()
    data object OnSignInClick : SignInIntent()
    data object OnSignUpClick : SignInIntent()
}
