package com.the_chance.data.comment

import com.the_chance.data.user.Author
import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: String,
    val postId: String,
    val totalLike: Int = 0,
    val author: Author,
    val content: String,
    val createAt: String,
)
