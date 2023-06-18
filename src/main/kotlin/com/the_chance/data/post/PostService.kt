package com.the_chance.data.post

import com.the_chance.data.user.UserTable
import com.the_chance.data.utils.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class PostService {

    suspend fun createPost(postCreatorId: UUID, postContent: String) = dbQuery {
        PostTable.insert {
            it[creatorId] = postCreatorId
            it[content] = postContent
        }
    }

    suspend fun getAllPost(): List<Post> {
        return dbQuery {
            (PostTable innerJoin UserTable)
                .slice(PostTable.columns + UserTable.columns)
                .selectAll()
                .map {
                Post(
                    it[PostTable.id].value.toString(),
                    it[PostTable.content],
                    it[PostTable.creationTime],
                    it[UserTable.fullName]
                )
            }
        }
    }

    suspend fun getPostById(postId: UUID): Post? {
        return dbQuery {
            (PostTable innerJoin UserTable)
                .slice(PostTable.columns + UserTable.columns)
                .select { PostTable.id eq postId }.singleOrNull()
                ?.let { post ->
                    Post(
                        post[PostTable.id].value.toString(),
                        post[PostTable.content],
                        post[PostTable.creationTime],
                        post[UserTable.fullName]
                    )
                }
        }
    }
}
