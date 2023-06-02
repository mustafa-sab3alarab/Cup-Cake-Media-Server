package com.the_chance.endpoints

import com.the_chance.controllers.PostsController
import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.utils.tryQuery
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.postsRoutes(postsController: PostsController) {

    post("/post") {
        tryQuery {
            val params = call.receiveParameters()
            val content = params["content"]?.trim()

            val newPost = postsController.tryCreatePost(content)
            call.respond(HttpStatusCode.Created, ServerResponse.success(newPost, successMessage = "Post created successfully"))
        }
    }

    get("/posts") {
        tryQuery {
            val posts = postsController.tryGetPosts()
            call.respond(HttpStatusCode.OK, ServerResponse.success(posts))
        }
    }

    get("/post/{postId}") {
            tryQuery {
                val postId = call.parameters["postId"]?.trim()

                val post = postsController.tryGetPostById(postId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(post))
            }
    }
}
