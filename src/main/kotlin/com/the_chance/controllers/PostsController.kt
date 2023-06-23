package com.the_chance.controllers

import com.the_chance.controllers.validation.isValidContent
import com.the_chance.controllers.validation.isValidUUID
import com.the_chance.data.post.Post
import com.the_chance.data.post.PostService
import com.the_chance.utils.FailedImageDeleteError
import com.the_chance.utils.FileNotExistError
import com.the_chance.utils.NoPostFoundError
import com.the_chance.utils.Unauthorized
import java.io.File


class PostsController(private val postService: PostService) {

    //region user post
    suspend fun createPost(creatorId: String?, imageId: String? = null, content: String) {
        isValidContent(content)
        val creatorUUID = isValidUUID(creatorId)
        val imageUUID = imageId?.let { isValidUUID(it) }
        postService.createPost(creatorUUID, imageUUID, content)
    }

    suspend fun deletePost(userId: String?, postId: String?) {
        val postUUID = isValidUUID(postId)
        postService.getPostById(postUUID)?.takeIf {
            it.author.id == userId
        }?.let {
            postService.deletePost(postUUID)
            it.image?.imageUrl?.let { url ->
                deleteImageFile(url)
            }
        } ?: throw Unauthorized
    }

    suspend fun getAllUserPosts(userId: String?): List<Post> {
        val userUUID = isValidUUID(userId)
        return postService.getAllUserPosts(userUUID)
    }

    //endregion

    //region public job
    suspend fun getPosts(): List<Post> {
        return postService.getAllPost()
    }

    suspend fun getPostById(postId: String?): Post {
        val postUUID = isValidUUID(postId)
        return postService.getPostById(postUUID) ?: throw NoPostFoundError()
    }

    //endregion

    private fun deleteImageFile(imageUrl: String) {
        val path = extractImageFileName(imageUrl)
        val file = File("public/images/$path")

        if (file.exists()) {
            if (!file.delete()) {
                throw FailedImageDeleteError
            }
        } else {
            throw FileNotExistError
        }
    }


    private fun extractImageFileName(url: String): String {
        val startIndex = url.lastIndexOf("/") + 1
        return url.substring(startIndex)
    }


}
