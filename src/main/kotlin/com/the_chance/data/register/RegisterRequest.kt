package com.the_chance.data.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val fullName: String,
    val phoneNumber: String,
    val password: String,
)