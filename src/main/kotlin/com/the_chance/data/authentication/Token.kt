package com.the_chance.data.authentication

import kotlinx.serialization.Serializable


@Serializable
data class Token(
    val token: String,
    val expireTime: Long
)