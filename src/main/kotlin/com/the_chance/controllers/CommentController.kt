package com.the_chance.controllers

import com.the_chance.controllers.validation.isValidContent
import com.the_chance.controllers.validation.isValidUUID
import com.the_chance.data.comment.Comment
import com.the_chance.data.comment.CommentRequest
import com.the_chance.data.comment.CommentService
import com.the_chance.utils.CommentNotFoundError
import com.the_chance.utils.Unauthorized
import java.util.UUID

class CommentController(private val commitService: CommentService) {

    suspend fun addComment(comment: CommentRequest) {
        isValidContent(comment.content)
        commitService.insertComment(
            isValidUUID(comment.userId),
            isValidUUID(comment.postId),
            comment.content
        )
    }

    suspend fun getCommentsPost(postId: String): List<Comment> {
        return commitService.getAllCommentByPostId(isValidUUID(postId))
    }

    suspend fun editeComment(userId: String, commentId: String, content: String): Comment {
        return commitService.getCommentById(isValidUUID(commentId)).let {
            it?.let {
                if (it.author.id == userId) {
                    commitService.editeComment(isValidUUID(commentId), content)
                } else throw Unauthorized
            } ?: throw CommentNotFoundError
        }

    }

    suspend fun deleteComment(commentId: String, userId: String) {
        val commentUUID = isValidUUID(commentId)
        executeWhenFoundComment(
            commentUUID, userId
        ) {
            commitService.deleteComment(isValidUUID(commentId))
        }


    }

    private suspend fun executeWhenFoundComment(
        commentUUID: UUID,
        userId: String,
        function: suspend () -> Any
    ) {
        commitService.getCommentById(commentUUID).let {
            it?.let {
                if (it.author.id == userId) {
                    function()
                } else throw Unauthorized
            } ?: throw CommentNotFoundError
        }
    }

}