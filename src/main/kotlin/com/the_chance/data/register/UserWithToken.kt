package com.the_chance.data.register

import com.the_chance.data.authentication.Token
import kotlinx.serialization.Serializable

@Serializable
data class UserWithToken(
    var id: String,
    val username: String,
    val fullName: String,
    val phoneNumber: String,
    val isActive: Boolean,
    val createdAt: Long,
    val token: Token
)