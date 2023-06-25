package com.the_chance.data.profle.skills

import com.the_chance.data.user.UserTable
import com.the_chance.utils.CREATE_AT
import com.the_chance.utils.SKILL
import com.the_chance.utils.USER_ID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object SkillsTable : UUIDTable() {
    val skill = text(SKILL)
    val createdAt = datetime(CREATE_AT).defaultExpression(CurrentDateTime)
    val userId = reference(USER_ID, UserTable.id)
}