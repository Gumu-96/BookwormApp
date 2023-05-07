package com.gumu.bookwormapp.domain.usecase

class ValidateName {
    operator fun invoke(name: String): Boolean {
        return name.isNotBlank()
    }
}
