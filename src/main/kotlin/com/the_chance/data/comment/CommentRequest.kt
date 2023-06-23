package com.the_chance.data.comment

data class CommentRequest(
    val postId: String,
    var userId: String,
    val content: String,
)