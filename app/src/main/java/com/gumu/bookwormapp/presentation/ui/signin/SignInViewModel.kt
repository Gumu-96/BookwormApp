package com.gumu.bookwormapp.presentation.ui.signin

import androidx.lifecycle.viewModelScope
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.data.repository.FirebaseAuthRepository
import com.gumu.bookwormapp.domain.common.onFailure
import com.gumu.bookwormapp.domain.common.onLoading
import com.gumu.bookwormapp.domain.common.onSuccess
import com.gumu.bookwormapp.domain.usecase.ValidateEmail
import com.gumu.bookwormapp.domain.usecase.ValidatePassword
import com.gumu.bookwormapp.presentation.navigation.Screen
import com.gumu.bookwormapp.presentation.ui.common.BaseViewModel
import com.gumu.bookwormapp.presentation.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val authRepository: FirebaseAuthRepository
) : BaseViewModel<SignInState, SignInEvent>() {
    override val uiState: StateFlow<SignInState> = _uiState.asStateFlow()

    init {
        checkUserSession()
    }

    override fun defaultState(): SignInState = SignInState()

    private fun checkUserSession() {
        _uiState.update { it.copy(isLoading = true) }
        if (authRepository.checkUserSession())
            sendEvent(UiEvent.NavigateTo(Screen.HomeScreen.route, Screen.SignInScreen.route))
        else
            _uiState.update { it.copy(isLoading = false) }
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
                emailError = if (validateEmail.invoke(it.email).not()) R.string.not_valid_email_error else null,
                passwordError = if (validatePassword.invoke(it.password).not()) R.string.minimum_characters_error else null
            )
        ) }
        if (_uiState.value.errorState == SignInErrorState()) {
            viewModelScope.launch {
                authRepository.signIn(_uiState.value.email, _uiState.value.password).collectLatest { result ->
                    result.onLoading {
                        _uiState.update { it.copy(isLoading = true) }
                    }.onSuccess {
                        sendEvent(UiEvent.NavigateTo(Screen.HomeScreen.route, Screen.SignInScreen.route))
                    }.onFailure {
                        _uiState.update { it.copy(isLoading = false) }
                        sendEvent(UiEvent.ShowToast(R.string.authentication_error))
                    }
                }
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
