package com.the_chance.endpoints

import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.data.utils.ServerResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.Exception

fun Routing.jobTitleRoute(jobTitleService: JobTitleService) {


    get("/jobTitles") {
        jobTitleService.getAllJobTitle().takeIf { it.isNotEmpty() }?.let { posts ->
            call.respond(HttpStatusCode.OK, ServerResponse.success(posts))
        } ?: call.respond(HttpStatusCode.NoContent)
    }

    post("/jobTitle") {
        try{
            val jobTitle = call.receiveParameters()["jobTitle"]?.trim().toString()

            val createdJobTitle = jobTitleService.insertJobTitle(jobTitle)

            call.respond(
                HttpStatusCode.Created,
                ServerResponse.success(createdJobTitle, successMessage = "Post created successfully")
            )
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Content should not be empty"))
        }
    }


}

