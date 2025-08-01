package com.gumu.bookwormapp.presentation.ui.signin

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.ValidationUtils
import com.gumu.bookwormapp.presentation.component.CustomOutlinedTextField
import com.gumu.bookwormapp.presentation.component.LoadingOverlay
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SignInScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    state: SignInState,
    onIntent: (SignInIntent) -> Unit
) {
    Scaffold { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            if (state.isLoading) {
                LoadingOverlay()
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    LogoSection(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    SignInSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        email = state.email,
                        onEmailChange = { onIntent(SignInIntent.OnEmailChange(it)) },
                        emailError = state.errorState.emailError,
                        password = state.password,
                        onPasswordChange = { onIntent(SignInIntent.OnPasswordChange(it)) },
                        passwordError = state.errorState.passwordError,
                        onSignInClick = { onIntent(SignInIntent.OnSignInClick) },
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SignUpSection(
                        onSignUpClick = { onIntent(SignInIntent.OnSignUpClick) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun LogoSection(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    with(sharedTransitionScope) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.bookworm),
                contentDescription = stringResource(id = R.string.app_logo_desc),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState("bookworm_logo"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.app_title_label),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SignInSection(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit,
    @StringRes emailError: Int?,
    password: String,
    onPasswordChange: (String) -> Unit,
    @StringRes passwordError: Int?,
    onSignInClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val localFocusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CustomOutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.email_field_label)) },
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
            label = { Text(text = stringResource(id = R.string.password_field_label)) },
            isPassword = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                autoCorrectEnabled = false
            ),
            keyboardActions = KeyboardActions(
                onDone = { localFocusManager.clearFocus() }
            ),
            isError = passwordError != null,
            errorMessage = passwordError?.let {
                stringResource(id = it, ValidationUtils.MINIMUM_PASSWORD_LENGTH)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        with(sharedTransitionScope) {
            Button(
                onClick = onSignInClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        sharedContentState = rememberSharedContentState("shared_button"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            ) {
                Text(text = stringResource(id = R.string.sign_in_button_label))
            }
        }
    }
}

@Composable
fun SignUpSection(
    onSignUpClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.new_user_label))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.sign_up_button_label),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable(onClick = onSignUpClick)
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun SignInPreview() {
    BookwormAppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(true) {
                SignInScreen(
                    state = SignInState(),
                    onIntent = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}
