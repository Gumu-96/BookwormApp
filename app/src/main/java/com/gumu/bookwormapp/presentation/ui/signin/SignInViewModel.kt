package com.gumu.bookwormapp.presentation.ui.signin

import com.google.firebase.auth.FirebaseAuth
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.usecase.ValidateEmail
import com.gumu.bookwormapp.domain.usecase.ValidatePassword
import com.gumu.bookwormapp.presentation.navigation.Screen
import com.gumu.bookwormapp.presentation.ui.common.BaseViewModel
import com.gumu.bookwormapp.presentation.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel<SignInState, SignInEvent>() {
    override val uiState: StateFlow<SignInState> = _uiState.asStateFlow()

    init {
        checkUserSession()
    }

    override fun defaultState(): SignInState = SignInState()

    private fun checkUserSession() {
        _uiState.update { it.copy(isLoading = true) }
        if (firebaseAuth.currentUser != null)
            sendEvent(UiEvent.NavigateTo(Screen.HomeScreen.route, Screen.SignInScreen.route))
        else
            _uiState.update { it.copy(isLoading = false) }
    }

    private fun onEmailChange(email: String) {
        _uiState.update { it.copy(
            email = email,
            emailError = if (validateEmail.invoke(email).not()) R.string.not_valid_email_error else null
        ) }
    }

    private fun onPasswordChange(password: String) {
        _uiState.update { it.copy(
            password = password,
            passwordError = if (validatePassword.invoke(password).not()) R.string.minimum_characters_error else null
        ) }
    }

    private fun onSignIn() {
        _uiState.update { it.copy(isLoading = true) }
        firebaseAuth.signInWithEmailAndPassword(_uiState.value.email, _uiState.value.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendEvent(UiEvent.NavigateTo(Screen.HomeScreen.route, Screen.SignInScreen.route))
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                    sendEvent(UiEvent.Error(R.string.authentication_error))
                }
            }
    }

    override fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnEmailChange -> onEmailChange(event.email.trim())
            is SignInEvent.OnPasswordChange -> onPasswordChange(event.password.trim())
            is SignInEvent.OnSignInClick -> onSignIn()
            is SignInEvent.OnSignUpClick -> sendEvent(UiEvent.NavigateTo(Screen.SignUpScreen.route))
        }
    }
}
