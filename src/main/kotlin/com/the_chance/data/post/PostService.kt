package com.the_chance.data.post

import com.the_chance.data.image.Image
import com.the_chance.data.image.ImageService
import com.the_chance.data.image.ImageTable
import com.the_chance.data.jobTitle.JobTitleTable
import com.the_chance.data.profle.ProfileTable
import com.the_chance.data.user.Author
import com.the_chance.data.user.UserTable
import com.the_chance.data.utils.dbQuery
import com.the_chance.utils.DeleteError
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class PostService(private val imageService: ImageService) {

    //region user post

    suspend fun createPost(postCreatorId: UUID, imageId: UUID?, postContent: String) = dbQuery {
        PostTable.insert {
            it[creatorId] = postCreatorId
            it[content] = postContent
            it[this.imageId] = imageId
        }
    }

    suspend fun deletePost(postId: UUID) {
        return dbQuery {
            val post = PostTable.select { PostTable.id eq postId }.singleOrNull()
            if (post != null) {
                val deleteResult = PostTable.deleteWhere { PostTable.id eq postId }
                if (deleteResult == 1) {
                    post[PostTable.imageId]?.value?.let { imageService.deleteImage(it) }
                } else {
                    throw DeleteError()
                }
            }
        }
    }

    suspend fun getAllUserPosts(userId: UUID): List<Post> {
        return dbQuery {
            (PostTable innerJoin UserTable innerJoin ProfileTable innerJoin JobTitleTable)
                .leftJoin(ImageTable, { PostTable.imageId }, { ImageTable.id })
                .slice(ImageTable.columns + PostTable.columns + UserTable.columns + ProfileTable.columns + JobTitleTable.title)
                .select { PostTable.creatorId eq userId }
                .orderBy(PostTable.createdAt to SortOrder.DESC)
                .map { it.toPost() }
        }
    }

    //endregion

    //region public job

    suspend fun getAllPost(): List<Post> {
        return dbQuery {
            (PostTable innerJoin UserTable innerJoin ProfileTable innerJoin JobTitleTable)
                .leftJoin(ImageTable, { PostTable.imageId }, { ImageTable.id })
                .slice(ImageTable.columns + PostTable.columns + UserTable.columns + ProfileTable.columns + JobTitleTable.title)
                .selectAll()
                .orderBy(PostTable.createdAt to SortOrder.DESC)
                .map { it.toPost() }
        }
    }


    suspend fun getPostById(postId: UUID): Post? {
        return dbQuery {
            (PostTable innerJoin UserTable innerJoin ProfileTable innerJoin JobTitleTable)
                .leftJoin(ImageTable, { PostTable.imageId }, { ImageTable.id })
                .slice(ImageTable.columns + PostTable.columns + UserTable.columns + ProfileTable.columns + JobTitleTable.title)
                .select { PostTable.id eq postId }.singleOrNull()?.toPost()
        }
    }

    //endregion

    private fun ResultRow.toPost(): Post {
        val imageId = this[PostTable.imageId]?.value?.toString()
        val imageUrl = this[ImageTable.url]

        val image = if (imageId != null) Image(imageId, imageUrl) else null
        return Post(
            id = this[PostTable.id].value.toString(),
            author = Author(
                id = this[UserTable.id].value.toString(),
                name = this[UserTable.fullName],
                jobTitle = this[JobTitleTable.title],
                avtar = this[ProfileTable.avatar],
            ),
            content = this[PostTable.content],
            image = image,
            createdAt = this[PostTable.createdAt].toString()
        )
    }


}
