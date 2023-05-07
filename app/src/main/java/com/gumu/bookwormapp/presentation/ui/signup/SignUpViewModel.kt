package com.gumu.bookwormapp.presentation.ui.signup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.data.remote.RemoteConstants
import com.gumu.bookwormapp.data.remote.dto.UserDto
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
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateName: ValidateName,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateRepeatedPassword: ValidateRepeatedPassword,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
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
        _uiState.update { it.copy(isLoading = true) }
        firebaseAuth
            .createUserWithEmailAndPassword(_uiState.value.email, _uiState.value.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAuth.currentUser?.uid?.let {
                        saveUserData(it)
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                    sendEvent(UiEvent.Error(R.string.registration_error))
                }
            }
    }

    private fun saveUserData(userId: String) {
        firebaseFirestore
            .collection(RemoteConstants.USERS_COLLECTION)
            .document(userId)
            .set(UserDto(_uiState.value.firstname, _uiState.value.lastname))
            .addOnSuccessListener {
                sendEvent(UiEvent.NavigateTo(Screen.HomeScreen.route, Screen.SignInScreen.route))
            }
            .addOnFailureListener {
                _uiState.update { it.copy(isLoading = false) }
                sendEvent(UiEvent.Error(R.string.registration_data_error))
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
