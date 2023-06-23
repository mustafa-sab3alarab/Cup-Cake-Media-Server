package com.the_chance.data.register

import com.the_chance.data.authentication.Token
import com.the_chance.data.profle.Profile
import kotlinx.serialization.Serializable

@Serializable
data class UserWithToken(
    var id: String,
    val username: String,
    val fullName: String,
    val email: String,
    val profile: Profile,
    val token: Token,
    val isActive: Boolean,
    val createdAt: String,
)