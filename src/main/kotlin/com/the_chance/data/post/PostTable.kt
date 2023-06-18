package com.the_chance.data.post

import com.the_chance.data.user.UserTable
import org.jetbrains.exposed.dao.id.UUIDTable
import java.time.Instant

object PostTable : UUIDTable() {
    val creatorId = reference("creatorId", UserTable.id)
    val content = text("content")
    val creationTime = long("createdAt").default(Instant.now().toEpochMilli())
}
