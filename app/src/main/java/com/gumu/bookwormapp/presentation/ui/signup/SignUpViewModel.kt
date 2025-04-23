package com.gumu.bookwormapp.presentation.ui.signup

import androidx.lifecycle.viewModelScope
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.onFailure
import com.gumu.bookwormapp.domain.common.onSuccess
import com.gumu.bookwormapp.domain.model.User
import com.gumu.bookwormapp.domain.usecase.ValidateSignUp
import com.gumu.bookwormapp.domain.usecase.auth.SaveNewUserDataUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SignUpUseCase
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
class SignUpViewModel @Inject constructor(
    private val validateSignUp: ValidateSignUp,
    private val signUpUseCase: SignUpUseCase,
    private val saveNewUserDataUseCase: SaveNewUserDataUseCase
) : BaseViewModel<SignUpState, SignUpIntent>() {
    override val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    override fun defaultState(): SignUpState = SignUpState()

    private fun onFirstnameChange(firstname: String) {
        _uiState.update { it.copy(
            firstname = firstname,
            errorState = it.errorState.copy(firstnameError = null)
        ) }
    }

    private fun onLastnameChange(lastname: String) {
        _uiState.update { it.copy(
            lastname = lastname,
            errorState = it.errorState.copy(lastnameError = null)
        ) }
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
            errorState = it.errorState.copy(passwordError = null, repeatedPasswordError = null)
        ) }
    }

    private fun onRepeatedPasswordChange(repeatedPassword: String) {
        _uiState.update { it.copy(
            repeatedPassword = repeatedPassword,
            errorState = it.errorState.copy(repeatedPasswordError = null)
        ) }
    }

    private fun onRegisterClick() {
        _uiState.update {
            val result = validateSignUp(
                name = it.firstname,
                lastname = it.lastname,
                email = it.email,
                password = it.password,
                confirmPassword = it.repeatedPassword
            )
            if (result.isSuccess()) {
                it.copy(errorState = SignUpErrorState())
            } else {
                it.copy(
                    errorState = SignUpErrorState(
                        firstnameError = result.errors[ValidateSignUp.NAME_FIELD],
                        lastnameError = result.errors[ValidateSignUp.LASTNAME_FIELD],
                        emailError = result.errors[ValidateSignUp.EMAIL_FIELD],
                        passwordError = result.errors[ValidateSignUp.PASSWORD_FIELD],
                        repeatedPasswordError = result.errors[ValidateSignUp.CONFIRM_PASSWORD_FIELD],
                    )
                )
            }
        }
        if (_uiState.value.errorState == SignUpErrorState()) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                signUpUseCase(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                ).onSuccess { userId ->
                    userId?.let {
                        saveUserData(it)
                    }
                }.onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    sendEvent(UiEvent.ShowToast(R.string.registration_error))
                }
            }
        }
    }

    private suspend fun saveUserData(userId: String) {
        saveNewUserDataUseCase(
            User(
                id = userId,
                firstname = _uiState.value.firstname,
                lastname = _uiState.value.lastname
            )
        ).onSuccess {
            sendEvent(UiEvent.Navigate(Screen.HomeScreen, Screen.SignInScreen))
        }.onFailure {
            _uiState.update { it.copy(isLoading = false) }
            sendEvent(UiEvent.ShowToast(R.string.registration_data_error))
        }
    }

    override fun onIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.OnEmailChange -> onEmailChange(intent.email.trim())
            is SignUpIntent.OnFirstnameChange -> onFirstnameChange(intent.firstname.trim())
            is SignUpIntent.OnLastnameChange -> onLastnameChange(intent.lastname.trim())
            is SignUpIntent.OnPasswordChange -> onPasswordChange(intent.password.trim())
            is SignUpIntent.OnRepeatedPasswordChange -> onRepeatedPasswordChange(intent.repeatedPassword.trim())
            is SignUpIntent.OnRegisterClick -> onRegisterClick()
            is SignUpIntent.OnBackClick -> sendEvent(UiEvent.NavigateBack)
        }
    }
}
