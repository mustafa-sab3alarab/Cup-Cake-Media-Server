package com.the_chance.data.comment

import com.the_chance.data.post.PostTable
import com.the_chance.data.user.UserTable
import com.the_chance.utils.CONTENT
import com.the_chance.utils.CREATE_AT
import com.the_chance.utils.POST_ID
import com.the_chance.utils.USER_ID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object CommentTable : UUIDTable() {
    val userId = reference(USER_ID, UserTable.id)
    val postId = reference(POST_ID, PostTable.id)
    val content = varchar(CONTENT, 2000)
    val createdAt = datetime(CREATE_AT).defaultExpression(CurrentDateTime)
}