package com.the_chance.endpoints.utils

import io.ktor.http.content.*
import java.io.File
import java.util.*


fun PartData.FileItem.saveFile(path: String): String {
    val fileBytes = streamProvider().readBytes()
    val fileExtension = originalFileName?.takeLastWhile { it != '.' }
    val fileName = UUID.randomUUID().toString() + "." + fileExtension
    val folder = File(path)
    if (!folder.exists()) {
        folder.mkdirs()
    }
    File("$path$fileName").writeBytes(fileBytes)
    return fileName
}
