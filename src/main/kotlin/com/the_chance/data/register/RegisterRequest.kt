package com.the_chance.data.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val fullName: String,
    val email: String,
    val password: String,
    val jobTitleId: Int,
)