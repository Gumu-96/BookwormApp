package com.gumu.bookwormapp.presentation.ui.signup

import androidx.lifecycle.viewModelScope
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.onFailure
import com.gumu.bookwormapp.domain.common.onLoading
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateSignUp: ValidateSignUp,
    private val signUpUseCase: SignUpUseCase,
    private val saveNewUserDataUseCase: SaveNewUserDataUseCase
) : BaseViewModel<SignUpState, SignUpEvent>() {
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
            val validationResult = validateSignUp(
                name = it.firstname,
                lastname = it.lastname,
                email = it.email,
                password = it.password,
                confirmPassword = it.repeatedPassword
            )
            when (validationResult) {
                is ValidateSignUp.Result.Failure -> {
                    it.copy(
                        errorState = SignUpErrorState(
                            firstnameError = validationResult.errors[ValidateSignUp.NAME_FIELD],
                            lastnameError = validationResult.errors[ValidateSignUp.LASTNAME_FIELD],
                            emailError = validationResult.errors[ValidateSignUp.EMAIL_FIELD],
                            passwordError = validationResult.errors[ValidateSignUp.PASSWORD_FIELD],
                            repeatedPasswordError = validationResult.errors[ValidateSignUp.CONFIRM_PASSWORD_FIELD],
                        )
                    )
                }
                is ValidateSignUp.Result.Success -> {
                    it.copy(errorState = SignUpErrorState())
                }
            }
        }
        if (_uiState.value.errorState == SignUpErrorState()) {
            viewModelScope.launch {
                signUpUseCase.invoke(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                ).collectLatest { result ->
                    result.onLoading {
                        _uiState.update { it.copy(isLoading = true) }
                    }.onSuccess { userId ->
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
    }

    private suspend fun saveUserData(userId: String) {
        saveNewUserDataUseCase.invoke(
            User(
                id = userId,
                firstname = _uiState.value.firstname,
                lastname = _uiState.value.lastname
            )
        ).collectLatest { result ->
            result.onSuccess {
                sendEvent(UiEvent.NavigateTo(Screen.HomeScreen.route, Screen.SignInScreen.route))
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
                sendEvent(UiEvent.ShowToast(R.string.registration_data_error))
            }
        }
    }

    override fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnEmailChange -> onEmailChange(event.email.trim())
            is SignUpEvent.OnFirstnameChange -> onFirstnameChange(event.firstname.trim())
            is SignUpEvent.OnLastnameChange -> onLastnameChange(event.lastname.trim())
            is SignUpEvent.OnPasswordChange -> onPasswordChange(event.password.trim())
            is SignUpEvent.OnRepeatedPasswordChange -> onRepeatedPasswordChange(event.repeatedPassword.trim())
            is SignUpEvent.OnRegisterClick -> onRegisterClick()
            is SignUpEvent.OnBackClick -> sendEvent(UiEvent.NavigateBack)
        }
    }
}
