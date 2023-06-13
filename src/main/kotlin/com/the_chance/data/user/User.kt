package com.the_chance.data.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: String,
    val username: String,
    val fullName: String,
    val phoneNumber: String,
    val isActive: Boolean,
    val createdAt: Long,
)