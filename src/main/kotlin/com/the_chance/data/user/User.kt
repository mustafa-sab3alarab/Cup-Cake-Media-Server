package com.the_chance.data.user

import com.the_chance.data.profle.Profile
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val fullName: String,
    val email: String,
    val profile: Profile,
    val isActive: Boolean,
    val createdAt: String,
)