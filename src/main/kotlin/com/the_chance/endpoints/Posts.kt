package com.the_chance.endpoints

import com.the_chance.data.post.PostService
import com.the_chance.data.utils.ServerResponse
import com.the_chance.utils.isValidUUID
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

    get("/posts") {
        postService.getAllPost().takeIf { it.isNotEmpty() }?.let { posts ->
            call.respond(HttpStatusCode.OK, ServerResponse.success(posts))
        } ?: call.respond(HttpStatusCode.NoContent)
    }

    get("/post/{idPost}") {

        suspend fun fetchPost(idPost: String) {
            postService.getPostById(idPost)?.let { post ->
                call.respond(HttpStatusCode.OK, ServerResponse.success(post))
            } ?: call.respond(HttpStatusCode.NotFound, ServerResponse.error("Opps!, this post not found."))
        }

        call.parameters["idPost"]?.let { id ->
            id.takeIf {
                isValidUUID(it)
            }?.let { idPost ->
                fetchPost(idPost)
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }
}