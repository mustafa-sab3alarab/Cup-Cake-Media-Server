package com.the_chance.data.user

import com.the_chance.utils.*
import org.jetbrains.exposed.dao.id.UUIDTable
import java.time.Instant

object UserTable : UUIDTable() {
    val username = varchar(USERNAME, 250).uniqueIndex()
    val password = varchar(PASSWORD, 250)
    val fullName = varchar(FULL_NAME, 250)
    val isActive = bool(IS_ACTIVE).default(true)
    val phoneNumber = varchar(PHONE_NUMBER, 20).uniqueIndex()
    val createdAt = long(CREATE_AT).default(Instant.now().toEpochMilli())

}