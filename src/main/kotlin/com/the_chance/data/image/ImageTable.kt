package com.the_chance.data.image

import org.jetbrains.exposed.dao.id.UUIDTable

object ImageTable : UUIDTable() {
    val url = text("url")
}