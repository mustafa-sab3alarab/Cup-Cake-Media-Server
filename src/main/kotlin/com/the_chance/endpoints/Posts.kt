package com.the_chance.endpoints

import com.the_chance.controllers.ImageController
import com.the_chance.controllers.PostsController
import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.utils.saveFile
import com.the_chance.endpoints.utils.tryQuery
import com.the_chance.utils.BASE_URL
import com.the_chance.utils.ID
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.postsRoutes(postsController: PostsController, imageController: ImageController) {

    authenticate("auth-jwt") {

        route("/user") {

            route("/post") {

                post {
                    tryQuery {
                        val principal = call.principal<JWTPrincipal>()
                        val userId = principal?.payload?.subject

                        processMultipartData(userId, call, imageController, postsController)

                        call.respond(
                            HttpStatusCode.Created,
                            ServerResponse.success(Unit, successMessage = "Post created successfully")
                        )
                    }
                }
                delete("/{$ID}") {
                    tryQuery {
                        val principal = call.principal<JWTPrincipal>()
                        val userId = principal?.payload?.subject

                        val postId = call.parameters[ID]
                        postsController.deletePost(userId, postId)
                        call.respond(HttpStatusCode.Accepted, ServerResponse.success(Unit,"Post deleted successfully"))
                    }

                }
                put("/$ID") { }

            }

            route("/posts") {
                get {
                    tryQuery {
                        val principal = call.principal<JWTPrincipal>()
                        val userId = principal?.subject

                        val jobs = postsController.getAllUserPosts(userId)
                        call.respond(HttpStatusCode.OK, ServerResponse.success(jobs))
                    }
                }
            }
        }

        route("/public") {

            route("/post") {
                get("/{postId}") {
                    tryQuery {
                        val postId = call.parameters["postId"]?.trim()

                        val post = postsController.getPostById(postId)
                        call.respond(HttpStatusCode.OK, ServerResponse.success(post))
                    }
                }
            }

            route("/posts") {
                get {
                    tryQuery {
                        val posts = postsController.getPosts()
                        call.respond(HttpStatusCode.OK, ServerResponse.success(posts))
                    }
                }
            }
        }
    }
}

private suspend fun processMultipartData(
    userId: String?,
    call: ApplicationCall,
    imageController: ImageController,
    postsController: PostsController
) {
    var fileName = ""
    var content = ""
    var isImageUploaded = false

    call.receiveMultipart().forEachPart { part ->
        when (part) {
            is PartData.FileItem -> {
                val isImage = imageController.isValidImage(part.originalFileName)
                if (isImage) {
                    fileName = part.saveFile("public/images/")
                    isImageUploaded = true
                }
            }

            is PartData.FormItem -> {
                content = part.value.trim()
            }

            else -> {}
        }
        part.dispose()
    }

    if (!isImageUploaded) {
        postsController.createPost(creatorId = userId, content = content)
    } else {
        val imageUrl = "$BASE_URL/public/images/$fileName"
        val image = imageController.insertImage(imageUrl)
        postsController.createPost(userId, image.id, content)
    }
}
