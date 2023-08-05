package com.gumu.bookwormapp.domain.usecase

import com.gumu.bookwormapp.domain.common.ValidationUtils

class ValidatePassword {
    operator fun invoke(password: String): Boolean {
        return if (password.isBlank()) false else password.length >= ValidationUtils.MINIMUM_PASSWORD_LENGTH
    }
}
