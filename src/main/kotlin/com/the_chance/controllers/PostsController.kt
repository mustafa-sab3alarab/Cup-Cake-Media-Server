package com.the_chance.controllers

import com.the_chance.controllers.validation.isValidContent
import com.the_chance.controllers.validation.isValidUUID
import com.the_chance.data.post.Post
import com.the_chance.data.post.PostService
import com.the_chance.utils.InValidContentError
import com.the_chance.utils.NoPostFoundError


class PostsController(private val postService: PostService) {
    suspend fun tryCreatePost(content: String?): Post {
        if (isValidContent(content)) {
            return postService.createPost(content!!)
        } else {
            throw InValidContentError()
        }
    }

    suspend fun tryGetPosts(): List<Post> {
        val posts = postService.getAllPost()
        return posts.ifEmpty { throw NoPostFoundError() }
    }

    suspend fun tryGetPostById(postId: String?): Post {
        val postUUID = isValidUUID(postId)
        return postService.getPostById(postUUID) ?: throw NoPostFoundError()
    }

}