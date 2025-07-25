package com.gumu.bookwormapp.presentation.ui.signin

import androidx.lifecycle.viewModelScope
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.onFailure
import com.gumu.bookwormapp.domain.common.onSuccess
import com.gumu.bookwormapp.domain.usecase.ValidateEmail
import com.gumu.bookwormapp.domain.usecase.ValidatePassword
import com.gumu.bookwormapp.domain.usecase.auth.CheckUserSessionUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SignInUseCase
import com.gumu.bookwormapp.presentation.navigation.Screen
import com.gumu.bookwormapp.presentation.ui.common.BaseViewModel
import com.gumu.bookwormapp.presentation.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val signInUseCase: SignInUseCase,
    private val checkUserSessionUseCase: CheckUserSessionUseCase
) : BaseViewModel<SignInState, SignInIntent>() {
    override val uiState: StateFlow<SignInState> = _uiState.asStateFlow()

    init {
        checkUserSession()
    }

    override fun defaultState(): SignInState = SignInState()

    private fun checkUserSession() {
        _uiState.update { it.copy(isLoading = true) }
        if (checkUserSessionUseCase.invoke()) {
            sendEvent(UiEvent.Navigate(Screen.HomeScreen, Screen.SignInScreen))
        } else {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun onEmailChange(email: String) {
        _uiState.update { it.copy(
            email = email,
            errorState = it.errorState.copy(emailError = null)
        ) }
    }

    private fun onPasswordChange(password: String) {
        _uiState.update { it.copy(
            password = password,
            errorState = it.errorState.copy(passwordError = null)
        ) }
    }

    private fun onSignIn() {
        _uiState.update { it.copy(
            errorState = SignInErrorState(
                emailError = if (validateEmail(it.email).not()) R.string.not_valid_email_error else null,
                passwordError = if (validatePassword(it.password).not()) R.string.minimum_characters_error else null
            )
        ) }
        if (_uiState.value.errorState == SignInErrorState()) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                signInUseCase(uiState.value.email, uiState.value.password).onSuccess {
                    sendEvent(UiEvent.Navigate(Screen.HomeScreen, Screen.SignInScreen))
                }.onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    sendEvent(UiEvent.ShowToast(R.string.authentication_error))
                }
            }
        }
    }

    override fun onIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.OnEmailChange -> onEmailChange(intent.email.trim())
            is SignInIntent.OnPasswordChange -> onPasswordChange(intent.password.trim())
            is SignInIntent.OnSignInClick -> onSignIn()
            is SignInIntent.OnSignUpClick -> sendEvent(UiEvent.Navigate(Screen.SignUpScreen))
        }
    }
}
