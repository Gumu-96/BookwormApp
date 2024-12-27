package com.gumu.bookwormapp.common.common_domain.utils

sealed interface BookwormResult<out T, out E : BaseError> {
    data class Success<out T, out E : BaseError>(val data: T) : BookwormResult<T, E>
    data class Error<out T, out E : BaseError>(val error: E) : BookwormResult<T, E>
}

inline fun <T, E : BaseError> BookwormResult<T, E>.onSuccess(action: (T) -> Unit): BookwormResult<T, E> {
    if (this is BookwormResult.Success) action(data)
    return this
}

inline fun <T, E : BaseError> BookwormResult<T, E>.onFailure(action: (E) -> Unit): BookwormResult<T, E> {
    if (this is BookwormResult.Error) action(error)
    return this
}
