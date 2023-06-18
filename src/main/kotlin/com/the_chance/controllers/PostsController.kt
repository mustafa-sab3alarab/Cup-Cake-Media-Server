package com.the_chance.controllers

import com.the_chance.controllers.validation.isValidContent
import com.the_chance.controllers.validation.isValidUUID
import com.the_chance.data.post.Post
import com.the_chance.data.post.PostService
import com.the_chance.utils.InValidContentError
import com.the_chance.utils.NoPostFoundError


class PostsController(private val postService: PostService) {
    suspend fun createPost(creatorId: String?, content: String?) {
        if (isValidContent(content)) {
            val creatorUUID = isValidUUID(creatorId)
            postService.createPost(creatorUUID, content!!)
        } else {
            throw InValidContentError()
        }
    }

    suspend fun getPosts(): List<Post> {
        return postService.getAllPost()
    }

    suspend fun getPostById(postId: String?): Post {
        val postUUID = isValidUUID(postId)
        return postService.getPostById(postUUID) ?: throw NoPostFoundError()
    }

}