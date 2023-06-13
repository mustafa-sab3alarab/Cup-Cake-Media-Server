package com.the_chance.controllers.mappers

import com.the_chance.data.authentication.Token
import com.the_chance.data.register.UserWithToken
import com.the_chance.data.user.User

fun User.toUserWithToken(token: Token): UserWithToken {
    return UserWithToken(
        id = id,
        username = username,
        fullName = fullName,
        phoneNumber = phoneNumber,
        isActive = isActive,
        createdAt = createdAt,
        token = token
    )
}
