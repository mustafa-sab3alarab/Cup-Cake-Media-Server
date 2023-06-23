package com.the_chance.data.user

import com.the_chance.data.profle.ProfileTable
import com.the_chance.utils.*
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object UserTable : UUIDTable() {
    val username = varchar(USERNAME, 250).uniqueIndex()
    val password = varchar(PASSWORD, 250)
    val fullName = varchar(FULL_NAME, 250)
    val isActive = bool(IS_ACTIVE).default(true)
    val email = varchar(EMAIL, 250).uniqueIndex()
    val createdAt = datetime(CREATE_AT).defaultExpression(CurrentDateTime)
    val profileId = uuid(PROFILE_ID).references(ProfileTable.id, onDelete = ReferenceOption.CASCADE).uniqueIndex()
}