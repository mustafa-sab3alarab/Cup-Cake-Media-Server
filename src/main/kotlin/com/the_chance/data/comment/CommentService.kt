package com.the_chance.data.comment

import com.the_chance.data.jobTitle.JobTitleTable
import com.the_chance.data.profle.ProfileTable
import com.the_chance.data.user.Author
import com.the_chance.data.user.UserTable
import com.the_chance.data.utils.dbQuery
import com.the_chance.utils.NotUpdateCommentError
import com.the_chance.utils.isSuccess
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.UUID

class CommentService {

    suspend fun insertComment(userId: UUID, postId: UUID, content: String) = dbQuery {
        CommentTable.insert {
            it[CommentTable.userId] = userId
            it[CommentTable.postId] = postId
            it[CommentTable.content] = content
        }

    }

    suspend fun getAllCommentByPostId(postId: UUID): List<Comment> = dbQuery {
        (UserTable innerJoin CommentTable innerJoin ProfileTable innerJoin JobTitleTable)
            .slice(CommentTable.columns + UserTable.columns + ProfileTable.columns + JobTitleTable.columns)
            .select {
                CommentTable.postId eq postId
            }.orderBy(CommentTable.createdAt to SortOrder.DESC).
            map {
                it.toComment()
            }

    }

    suspend fun getCommentById(commentId: UUID): Comment? = dbQuery {
        (UserTable innerJoin CommentTable innerJoin ProfileTable innerJoin JobTitleTable)
            .slice(CommentTable.columns + UserTable.columns + ProfileTable.columns + JobTitleTable.columns)
            .select {
                CommentTable.id eq commentId
            }.singleOrNull()?.toComment()
    }

    suspend fun deleteComment(commentId: UUID) = dbQuery {
        CommentTable.deleteWhere { CommentTable.id eq commentId }
    }


    suspend fun editeComment(commentId: UUID, content: String): Comment = dbQuery {
        CommentTable.update({ CommentTable.id eq commentId }) {
            it[CommentTable.content] = content
        }.takeIf { it.isSuccess() }?.let {
            getCommentById(commentId)
        } ?: throw NotUpdateCommentError

    }

    private fun ResultRow.toComment() = Comment(
        id = this[CommentTable.id].value.toString(),
        author = Author(
            id = this[UserTable.id].value.toString(),
            name = this[UserTable.fullName],
            jobTitle = this[JobTitleTable.title],
            avtar = this[ProfileTable.avatar],
        ),
        postId = this[CommentTable.postId].value.toString(),
        content = this[CommentTable.content],
        createAt = this[CommentTable.createdAt].toString(),
    )


}