package com.gumu.bookwormapp.domain.usecase

import android.util.Patterns
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.UiText
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class ValidateSignUp {
    operator fun invoke(
        name: String,
        lastname: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Result {
        val errors = mutableMapOf<String, UiText>()

        if (name.isBlank()) {
            errors[NAME_FIELD] = UiText.StringResource(R.string.required_field_error)
        }
        if (lastname.isBlank()) {
            errors[LASTNAME_FIELD] = UiText.StringResource(R.string.required_field_error)
        }
        validateEmail(email)?.let {
            errors[EMAIL_FIELD] = it
        }
        validatePassword(password)?.let {
            errors[PASSWORD_FIELD] = it
        }
        validateConfirmPassword(password, confirmPassword)?.let {
            errors[CONFIRM_PASSWORD_FIELD] = it
        }

        return if (errors.isEmpty()) Result.Success else Result.Failure(errors.toMap())
    }

    private fun validateEmail(email: String): UiText? {
        return when {
            email.isBlank() -> {
                UiText.StringResource(R.string.required_field_error)
            }
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                UiText.StringResource(R.string.not_valid_email_error)
            }
            else -> null
        }
    }

    private fun validatePassword(password: String): UiText? {
        return when {
            password.isBlank() -> {
                UiText.StringResource(R.string.required_field_error)
            }
            password.length < MINIMUM_PASSWORD_LENGTH -> {
                UiText.StringResource(R.string.minimum_characters_error, MINIMUM_PASSWORD_LENGTH)
            }
            else -> null
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): UiText? {
        return when {
            confirmPassword.isBlank() -> {
                UiText.StringResource(R.string.required_field_error)
            }
            password.isNotBlank() && password != confirmPassword -> {
                UiText.StringResource(R.string.passwords_do_not_match_error)
            }
            else -> null
        }
    }

    sealed class Result {
        object Success : Result()
        data class Failure(val errors: Map<String, UiText>) : Result()

        @OptIn(ExperimentalContracts::class)
        fun isSuccess(): Boolean {
            contract {
                returns(false) implies (this@Result is Failure)
                returns(true) implies (this@Result is Success)
            }
            return this is Success
        }
    }

    companion object {
        const val NAME_FIELD = "name"
        const val LASTNAME_FIELD = "lastname"
        const val EMAIL_FIELD = "email"
        const val PASSWORD_FIELD = "password"
        const val CONFIRM_PASSWORD_FIELD = "confirm_password"
        private const val MINIMUM_PASSWORD_LENGTH = 6
    }
}
