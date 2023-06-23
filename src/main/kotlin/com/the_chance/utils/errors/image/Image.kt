package com.the_chance.utils.errors.image

import com.the_chance.utils.*
import io.ktor.server.plugins.statuspages.*

fun StatusPagesConfig.imageErrorsException() {

    exception<IllegalImage> { call, _ ->
        call.badRequest("Uploaded image format is not supported.")
    }

    exception<FileNotExistError> { call, _ ->
        call.badRequest("Image File does not exist")
    }

    exception<FailedImageDeleteError> { call, _ ->
        call.badRequest("Failed to delete the file")
    }
}