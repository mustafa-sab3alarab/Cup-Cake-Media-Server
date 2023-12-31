package com.the_chance.data.post

import com.the_chance.data.image.ImageTable
import com.the_chance.data.user.UserTable
import com.the_chance.utils.CONTENT
import com.the_chance.utils.CREATE_AT
import com.the_chance.utils.IMAGE_ID
import com.the_chance.utils.USER_ID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object PostTable : UUIDTable() {
    val creatorId = reference(USER_ID, UserTable.id)
    val imageId = reference(IMAGE_ID, ImageTable.id).nullable()
    val content = text(CONTENT)
    val createdAt = datetime(CREATE_AT).defaultExpression(CurrentDateTime)
}
