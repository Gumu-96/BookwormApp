package com.gumu.bookwormapp.domain.usecase

import android.util.Patterns

class ValidateEmail {
    operator fun invoke(email: String): Boolean {
        return if (email.isBlank()) false else Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
