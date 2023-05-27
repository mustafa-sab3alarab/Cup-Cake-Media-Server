package com.the_chance.endpoints

import com.the_chance.data.post.PostService
import com.the_chance.data.utils.ServerResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.postsRoutes(postService: PostService) {

    post("/post") {
        val params = call.receiveParameters()
        val content = params["content"]?.trim()

        if (content.isNullOrEmpty()) {
            call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Content should not be empty"))
        } else {
            val newPost = postService.createPost(content)
            call.respond(HttpStatusCode.Created, ServerResponse.success(newPost, successMessage = "Post created successfully"))
        }
    }
}