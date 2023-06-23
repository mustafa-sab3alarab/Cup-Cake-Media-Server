package com.the_chance.data.user

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val id: String,
    val name: String,
    val jobTitle: String,
    val avtar: String,
)
