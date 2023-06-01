package com.the_chance.data.jobTitle

import org.jetbrains.exposed.dao.id.IntIdTable

object JobTitleTable : IntIdTable() {
    val title = varchar("title", 250).uniqueIndex()
}