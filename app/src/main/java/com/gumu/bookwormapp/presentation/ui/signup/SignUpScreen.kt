package com.gumu.bookwormapp.presentation.ui.signup

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.ValidationUtils
import com.gumu.bookwormapp.presentation.component.CustomOutlinedTextField
import com.gumu.bookwormapp.presentation.component.LoadingOverlay
import com.gumu.bookwormapp.presentation.component.NavigateBackTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit
) {
    Scaffold(
        topBar = {
            NavigateBackTopAppBar(
                title = { Text(text = stringResource(id = R.string.sign_up_screen_title_label)) },
                onBackClick = { onEvent(SignUpEvent.OnBackClick) }
            )
        }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            if (state.isLoading) {
                LoadingOverlay()
            } else {
                SignUpForm(
                    firstname = state.firstname,
                    lastname = state.lastname,
                    email = state.email,
                    password = state.password,
                    repeatedPassword = state.repeatedPassword,
                    onFirstnameChange = { onEvent(SignUpEvent.OnFirstnameChange(it)) },
                    onLastnameChange = { onEvent(SignUpEvent.OnLastnameChange(it)) },
                    onEmailChange = { onEvent(SignUpEvent.OnEmailChange(it)) },
                    onPasswordChange = { onEvent(SignUpEvent.OnPasswordChange(it)) },
                    onRepeatedPasswordChange = { onEvent(SignUpEvent.OnRepeatedPasswordChange(it)) },
                    firstnameError = state.firstnameError,
                    lastnameError = state.lastnameError,
                    emailError = state.emailError,
                    passwordError = state.passwordError,
                    repeatedPasswordError = state.repeatedPasswordError,
                    onRegisterClick = { onEvent(SignUpEvent.OnRegisterClick) }
                )
            }
        }
    }
}

@Composable
fun SignUpForm(
    firstname: String,
    lastname: String,
    email: String,
    password: String,
    repeatedPassword: String,
    onFirstnameChange: (String) -> Unit,
    onLastnameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatedPasswordChange: (String) -> Unit,
    @StringRes firstnameError: Int?,
    @StringRes lastnameError: Int?,
    @StringRes emailError: Int?,
    @StringRes passwordError: Int?,
    @StringRes repeatedPasswordError: Int?,
    onRegisterClick: () -> Unit
) {
    val localFocusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bookworm),
            contentDescription = stringResource(id = R.string.app_logo_desc),
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(75.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlinedTextField(
            value = firstname,
            onValueChange = onFirstnameChange,
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.sign_up_first_name_field_label)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = firstnameError != null,
            errorMessage = firstnameError?.let { stringResource(id = it) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlinedTextField(
            value = lastname,
            onValueChange = onLastnameChange,
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.sign_up_last_name_field_label)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = lastnameError != null,
            errorMessage = lastnameError?.let { stringResource(id = it) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.sign_up_email_field_label)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = emailError != null,
            errorMessage = emailError?.let { stringResource(id = it) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.sign_up_password_field_label)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            ),
            isPassword = true,
            isError = passwordError != null,
            errorMessage = passwordError?.let { stringResource(
                id = it,
                ValidationUtils.MINIMUM_PASSWORD_LENGTH
            ) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlinedTextField(
            value = repeatedPassword,
            onValueChange = onRepeatedPasswordChange,
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.sign_up_confirm_password_field_label)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { localFocusManager.clearFocus() }
            ),
            isPassword = true,
            isError = repeatedPasswordError != null,
            errorMessage = repeatedPasswordError?.let { stringResource(id = it) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onRegisterClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
            enabled = firstname.isNotBlank() and lastname.isNotBlank() and
                    email.isNotBlank() and password.isNotBlank() and repeatedPassword.isNotBlank() and
                    (emailError == null) and (passwordError == null) and (repeatedPasswordError == null),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.create_account_button_label))
        }
    }
}
