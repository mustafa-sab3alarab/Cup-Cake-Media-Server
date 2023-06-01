package com.the_chance.endpoints

import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.data.utils.ServerResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.jobTitleRoute(jobTitleService: JobTitleService) {


    get("/jobTitles") {
        jobTitleService.getAllJobTitle().takeIf { it.isNotEmpty() }?.let { posts ->
            call.respond(HttpStatusCode.OK, ServerResponse.success(posts))
        } ?: call.respond(HttpStatusCode.NoContent)
    }


}

