package com.gumu.bookwormapp.presentation.ui.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.UiText
import com.gumu.bookwormapp.presentation.component.CustomOutlinedTextField
import com.gumu.bookwormapp.presentation.component.LoadingOverlay
import com.gumu.bookwormapp.presentation.component.NavigateBackTopAppBar
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SignUpScreen(
    state: SignUpState,
    onIntent: (SignUpIntent) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            NavigateBackTopAppBar(
                title = { Text(text = stringResource(id = R.string.sign_up_screen_title_label)) },
                onBackClick = { onIntent(SignUpIntent.OnBackClick) }
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
                    onFirstnameChange = { onIntent(SignUpIntent.OnFirstnameChange(it)) },
                    onLastnameChange = { onIntent(SignUpIntent.OnLastnameChange(it)) },
                    onEmailChange = { onIntent(SignUpIntent.OnEmailChange(it)) },
                    onPasswordChange = { onIntent(SignUpIntent.OnPasswordChange(it)) },
                    onRepeatedPasswordChange = { onIntent(SignUpIntent.OnRepeatedPasswordChange(it)) },
                    firstnameError = state.errorState.firstnameError,
                    lastnameError = state.errorState.lastnameError,
                    emailError = state.errorState.emailError,
                    passwordError = state.errorState.passwordError,
                    repeatedPasswordError = state.errorState.repeatedPasswordError,
                    onRegisterClick = { onIntent(SignUpIntent.OnRegisterClick) },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
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
    firstnameError: UiText?,
    lastnameError: UiText?,
    emailError: UiText?,
    passwordError: UiText?,
    repeatedPasswordError: UiText?,
    onRegisterClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val localFocusManager = LocalFocusManager.current

    with(sharedTransitionScope) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bookworm),
                contentDescription = stringResource(id = R.string.app_logo_desc),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(75.dp)
                    .sharedElement(
                        state = rememberSharedContentState("bookworm_logo"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
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
                errorMessage = firstnameError?.asString(),
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
                errorMessage = lastnameError?.asString(),
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
                errorMessage = emailError?.asString(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.sign_up_password_field_label)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
                ),
                isPassword = true,
                isError = passwordError != null,
                errorMessage = passwordError?.asString(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = repeatedPassword,
                onValueChange = onRepeatedPasswordChange,
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.sign_up_confirm_password_field_label)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.clearFocus() }
                ),
                isPassword = true,
                isError = repeatedPasswordError != null,
                errorMessage = repeatedPasswordError?.asString(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onRegisterClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        state = rememberSharedContentState("shared_button"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            ) {
                Text(text = stringResource(id = R.string.create_account_button_label))
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun SignUpScreenPreview() {
    BookwormAppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(true) {
                SignUpScreen(
                    state = SignUpState(),
                    onIntent = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}
