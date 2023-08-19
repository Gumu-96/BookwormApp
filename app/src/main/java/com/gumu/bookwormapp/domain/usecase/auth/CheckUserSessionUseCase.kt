package com.gumu.bookwormapp.domain.usecase.auth

interface CheckUserSessionUseCase {
    operator fun invoke(): Boolean
}
