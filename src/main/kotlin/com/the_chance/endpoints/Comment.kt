package com.the_chance.endpoints

import com.the_chance.controllers.CommentController
import com.the_chance.controllers.JobTitleController
import com.the_chance.data.comment.CommentRequest
import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.utils.tryQuery
import com.the_chance.utils.COMMENT_ID
import com.the_chance.utils.CONTENT
import com.the_chance.utils.POST_ID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.Exception

fun Routing.commentRoute(commentController: CommentController) {

    authenticate("auth-jwt") {

        post("/post/{$POST_ID}/comment") {
            val principal = call.principal<JWTPrincipal>()
            val comment = CommentRequest(
                postId = call.parameters[POST_ID]?.trim().orEmpty(),
                userId = principal?.payload?.subject.orEmpty(),
                content = call.receiveParameters()[CONTENT]?.trim().orEmpty()
            )
            commentController.addComment(comment)
            call.respond(
                HttpStatusCode.Created, ServerResponse.success(true, successMessage = "Comment added successfully")
            )
        }

        get("/post/{$POST_ID}/comments") {
            val postId = call.parameters[POST_ID]?.trim().orEmpty()
            commentController.getCommentsPost(postId).let { comments ->
                call.respond(HttpStatusCode.OK, ServerResponse.success(comments))
            }
        }

        put("/comment/{$COMMENT_ID}") {
            val principal = call.principal<JWTPrincipal>()

            commentController.editeComment(
                principal?.payload?.subject.orEmpty(),
                call.parameters[COMMENT_ID]?.trim().orEmpty(),
                call.receiveParameters()[CONTENT]?.trim().orEmpty()
            ).let { comments ->
                call.respond(HttpStatusCode.OK, ServerResponse.success(comments))
            }
        }

        delete("/comment/{$COMMENT_ID}") {
            val principal = call.principal<JWTPrincipal>()
            commentController.deleteComment(
                call.parameters[COMMENT_ID]?.trim().orEmpty(),
                principal?.payload?.subject.orEmpty(),
            )
            call.respond(HttpStatusCode.OK, ServerResponse.success(true, "Delete Comment successfully"))

        }

    }
}