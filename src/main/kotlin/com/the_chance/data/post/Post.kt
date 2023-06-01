package com.the_chance.data.post

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: String,
    val content: String,
    val createdAt: Long
)