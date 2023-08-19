package com.gumu.bookwormapp.data.remote.dto

import com.gumu.bookwormapp.domain.model.User

data class UserDto(
    val firstname: String,
    val lastname: String
)

fun User.toDto() =
    UserDto(
        firstname = firstname,
        lastname = lastname
    )

fun UserDto.toDomain(id: String) =
    User(
        id = id,
        firstname = firstname,
        lastname = lastname
    )
