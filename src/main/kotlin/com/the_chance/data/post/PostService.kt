package com.the_chance.data.post

import com.the_chance.data.utils.dbQuery
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.select
import java.util.UUID

class PostService(database: Database) {

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

    suspend fun getAllPost(): List<Post> {
        return dbQuery {
            PostTable.selectAll().map {
                Post(
                    it[PostTable.id].value.toString(),
                    it[PostTable.content],
                    it[PostTable.creationTime],
                )
            }
        }
    }

    suspend fun getPostById(postId: UUID): Post? {
        return dbQuery {
            PostTable.select { PostTable.id eq postId }.singleOrNull()
                ?.let { post ->
                    Post(
                        post[PostTable.id].value.toString(),
                        post[PostTable.content],
                        post[PostTable.creationTime]
                    )
                }
        }
    }


}