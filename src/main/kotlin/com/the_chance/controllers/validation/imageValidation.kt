package com.the_chance.controllers.validation

import com.the_chance.utils.IllegalImage

fun imageValidation(name: String?) : Boolean {
    val extension = name?.substringAfterLast(".", "")
    val isImage = extension?.let { it in listOf("jpg", "jpeg", "png") } ?: false
    return if (isImage) true else throw IllegalImage
}