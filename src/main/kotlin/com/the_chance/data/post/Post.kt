package com.the_chance.data.post

import com.the_chance.data.image.Image
import com.the_chance.data.user.Author
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: String = "",
    val author: Author,
    val content: String,
    val image: Image? = null,
    val createdAt: String
)
