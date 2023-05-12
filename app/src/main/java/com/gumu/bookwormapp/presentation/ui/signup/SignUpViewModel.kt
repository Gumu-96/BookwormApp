package com.gumu.bookwormapp.presentation.ui.signup

import androidx.lifecycle.viewModelScope
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.data.repository.FirebaseAuthRepository
import com.gumu.bookwormapp.domain.common.onFailure
import com.gumu.bookwormapp.domain.common.onLoading
import com.gumu.bookwormapp.domain.common.onSuccess
import com.gumu.bookwormapp.domain.usecase.ValidateEmail
import com.gumu.bookwormapp.domain.usecase.ValidateName
import com.gumu.bookwormapp.domain.usecase.ValidatePassword
import com.gumu.bookwormapp.domain.usecase.ValidateRepeatedPassword
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
    private val validateName: ValidateName,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateRepeatedPassword: ValidateRepeatedPassword,
    private val authRepository: FirebaseAuthRepository
) : BaseViewModel<SignUpState, SignUpEvent>() {
    override val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    override fun defaultState(): SignUpState = SignUpState()

    private fun onFirstnameChange(firstname: String) {
        _uiState.update { it.copy(
            firstname = firstname,
            firstnameError =
                if (validateName.invoke(firstname).not()) R.string.required_field_error
                else null
        ) }
    }

    private fun onLastnameChange(lastname: String) {
        _uiState.update { it.copy(
            lastname = lastname,
            lastnameError =
                if (validateName.invoke(lastname).not()) R.string.required_field_error
                else null
        ) }
    }

    private fun onEmailChange(email: String) {
        _uiState.update { it.copy(
            email = email,
            emailError =
                if (validateEmail.invoke(email).not()) R.string.not_valid_email_error
                else null
        ) }
    }

    private fun onPasswordChange(password: String) {
        _uiState.update { it.copy(
            password = password,
            passwordError =
                if (validatePassword.invoke(password).not()) R.string.minimum_characters_error
                else null
        ) }
        if (_uiState.value.repeatedPassword.isNotBlank()) validateStateRepeatedPassword()
    }

    private fun onRepeatedPasswordChange(repeatedPassword: String) {
        _uiState.update { it.copy(repeatedPassword = repeatedPassword) }
        validateStateRepeatedPassword()
    }

    private fun validateStateRepeatedPassword() {
        _uiState.update { it.copy(
            repeatedPasswordError =
                if (validateRepeatedPassword.invoke(_uiState.value.password, _uiState.value.repeatedPassword).not())
                R.string.passwords_do_not_match_error else null
        ) }
    }

    private fun onRegisterClick() {
        viewModelScope.launch {
            authRepository.registerUser(
                email = _uiState.value.email,
                password = _uiState.value.password
            ).collectLatest { result ->
                result.onLoading {
                    _uiState.update { it.copy(isLoading = true) }
                }.onSuccess {  userId ->
                    userId?.let {
                        saveUserData(it)
                    }
                }.onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    sendEvent(UiEvent.Error(R.string.registration_error))
                }
            }
        }
    }

    private suspend fun saveUserData(userId: String) {
        authRepository.saveNewUserData(
            userId = userId,
            firstname = _uiState.value.firstname,
            lastname = _uiState.value.lastname
        ).collectLatest { result ->
            result.onSuccess {
                sendEvent(UiEvent.NavigateTo(Screen.HomeScreen.route, Screen.SignInScreen.route))
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
                sendEvent(UiEvent.Error(R.string.registration_data_error))
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
