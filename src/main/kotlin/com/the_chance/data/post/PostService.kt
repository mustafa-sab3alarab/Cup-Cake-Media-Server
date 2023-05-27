package com.the_chance.data.post

import com.the_chance.data.utils.dbQuery
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class PostService(private val database: Database) {

    init {
        transaction(database) {
            SchemaUtils.create(PostTable)
        }
    }

    suspend fun createPost(postContent: String): Post = dbQuery {
        val newPost = PostTable.insert {
            it[content] = postContent
        }

        Post(
            id = newPost[PostTable.id].value.toString(),
            content = newPost[PostTable.content],
            createdAt = newPost[PostTable.creationTime]
        )
    }
}