package com.the_chance.utils

import com.the_chance.data.utils.ServerResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.badRequest(errorMessage: String) {
    this.respond(HttpStatusCode.BadRequest, ServerResponse.error(errorMessage))
}