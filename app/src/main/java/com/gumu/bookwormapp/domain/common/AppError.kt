package com.gumu.bookwormapp.domain.common

data class AppError(
    val cause: Throwable,
    val code: Int? = null
)
