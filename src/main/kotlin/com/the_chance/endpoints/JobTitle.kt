package com.the_chance.endpoints

import com.the_chance.data.jobTitle.JobTitle
import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.data.utils.ServerResponse
import com.the_chance.utils.isInt
import com.the_chance.utils.isValidUUID
import com.the_chance.utils.validationTitle
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.jobTitleRoute(jobTitleService: JobTitleService) {


    post("/jobTitle") {

        suspend fun createJobTitle(title: String) = jobTitleService.createJobTitle(title)

        suspend fun handleJobTitleResult(result: Pair<JobTitle, Boolean>) {
            result.takeIf { it.second }?.let { jobTitleCreated ->
                call.respond(HttpStatusCode.Created, ServerResponse.success(jobTitleCreated.first))
            } ?: call.respond(HttpStatusCode.Conflict, ServerResponse.success(result.first))
        }

        call.receiveParameters()["jobTitle"]?.let { title ->
            title.takeIf { validationTitle(it) == null }?.let {
                handleJobTitleResult(createJobTitle(title))
            } ?: call.respond(HttpStatusCode.BadRequest, ServerResponse.error(
                    validationTitle(title) ?: "Failed to create job title")
            )
        } ?: call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Title is required"))

    }

    get("/jobTitles") {
        jobTitleService.getAllJobTitle().takeIf { it.isNotEmpty() }?.let { posts ->
            call.respond(HttpStatusCode.OK, ServerResponse.success(posts))
        } ?: call.respond(HttpStatusCode.NoContent)
    }

    delete("/jobTitle/{idJobTitle}") {

        suspend fun deleteJobTitle(idJobTitle: Int) {
            jobTitleService.deleteJobTitleById(idJobTitle).takeIf { it }?.let { result ->
                call.respond(HttpStatusCode.OK, ServerResponse.success(result))
            } ?: call.respond(HttpStatusCode.NotFound, ServerResponse.error("Opps!, this job title not found."))
        }

        call.parameters["idJobTitle"]?.let { id ->
            id.takeIf {
                it.isInt()
            }?.let { idJobTitle ->
                deleteJobTitle(idJobTitle.toInt())
            } ?: call.respond(HttpStatusCode.BadRequest)
        }

    }

}

