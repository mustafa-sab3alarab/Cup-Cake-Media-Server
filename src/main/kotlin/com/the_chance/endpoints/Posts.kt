package com.the_chance.endpoints

import com.the_chance.controllers.PostsController
import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.utils.tryQuery
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.postsRoutes(postsController: PostsController) {

    authenticate("auth-jwt") {

        post("/post") {
            tryQuery {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject

                val params = call.receiveParameters()
                val content = params["content"]?.trim()

                postsController.createPost(userId, content)

                call.respond(
                    HttpStatusCode.Created,
                    ServerResponse.success(Unit, successMessage = "Post created successfully")
                )
            }
        }

        get("/posts") {
            tryQuery {
                val posts = postsController.getPosts()
                call.respond(HttpStatusCode.OK, ServerResponse.success(posts))
            }
        }

        get("/post/{postId}") {
            tryQuery {
                val postId = call.parameters["postId"]?.trim()

                val post = postsController.getPostById(postId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(post))
            }
        }
    }
}
