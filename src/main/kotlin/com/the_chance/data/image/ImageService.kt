package com.the_chance.data.image

import com.the_chance.data.utils.dbQuery
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import java.util.*

class ImageService {

    suspend fun insertImage(imageUrl: String): Image = dbQuery {
        val newImage = ImageTable.insert {
            it[this.url] = imageUrl
        }
        Image(
            id = newImage[ImageTable.id].value.toString(),
            imageUrl = newImage[ImageTable.url]
        )
    }

    fun deleteImage(imageId: UUID) {
        ImageTable.deleteWhere { ImageTable.id eq imageId }
    }
}