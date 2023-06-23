package com.the_chance.data.image

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: String? = null,
    val imageUrl: String? = null
)