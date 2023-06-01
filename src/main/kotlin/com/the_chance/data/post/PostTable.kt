package com.the_chance.data.post

import org.jetbrains.exposed.dao.id.UUIDTable
import java.time.Instant

object PostTable : UUIDTable() {
    val content = text("content")
    val creationTime = long("createdAt").default(Instant.now().toEpochMilli())
}