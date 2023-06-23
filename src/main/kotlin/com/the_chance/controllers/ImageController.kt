package com.the_chance.controllers

import com.the_chance.controllers.validation.imageValidation
import com.the_chance.data.image.Image
import com.the_chance.data.image.ImageService


class ImageController(private val imageService: ImageService) {

    suspend fun insertImage(imageUrl: String): Image {
        return imageService.insertImage(imageUrl)
    }

    fun isValidImage(originalFileName: String?): Boolean {
        return imageValidation(originalFileName)
    }

}