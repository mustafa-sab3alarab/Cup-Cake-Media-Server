package com.the_chance.endpoints.utils

import com.the_chance.data.utils.ServerResponse
import com.the_chance.utils.InValidContentError
import com.the_chance.utils.InValidIDError
import com.the_chance.utils.NoPostFoundError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

suspend fun PipelineContext<Unit, ApplicationCall>.tryQuery(query: suspend () -> Unit) {
    try {
        query()
    } catch (throwable: InValidContentError) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Content should not be empty"))
    } catch (throwable: NoPostFoundError) {
        call.respond(HttpStatusCode.NotFound, ServerResponse.error("no post found"))
    } catch (throwable: InValidIDError) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.error("invalid id"))
    }
}