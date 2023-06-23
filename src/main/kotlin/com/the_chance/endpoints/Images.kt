package com.the_chance.endpoints

import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.utils.tryQuery
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Routing.imagesRoutes() {

    get("/public/images/{fileName}") {
        tryQuery {
            val fileName = call.parameters["fileName"]

            val file = File("public/images/$fileName")
            if (file.exists()) {
                call.respondFile(file)
            } else {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("File not found"))
            }
        }
    }

}
