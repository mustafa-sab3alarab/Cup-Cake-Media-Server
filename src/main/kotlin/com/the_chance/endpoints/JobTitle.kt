package com.the_chance.endpoints

import com.the_chance.controllers.JobTitleController
import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.utils.tryQuery
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.Exception

fun Routing.jobTitleRoute(jobTitleController: JobTitleController) {

    authenticate("auth-jwt") {

        get("/jobTitles") {
            tryQuery {
                val jobTitles = jobTitleController.getAllJobTitles()
                call.respond(HttpStatusCode.OK, ServerResponse.success(jobTitles))
            }
        }

        //todo this end point is a temporary solution and should be removed in the future
        post("/jobTitle") {
            try {
                val jobTitle = call.receiveParameters()["jobTitle"]?.trim()

                if (jobTitle.isNullOrEmpty().not()) {
                    val createdJobTitle = jobTitleController.createJobTitle(jobTitle!!)

                    call.respond(
                            HttpStatusCode.Created,
                            ServerResponse.success(createdJobTitle, successMessage = "Post created successfully")
                    )

                } else {
                    call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Content should not be empty"))
                }

            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Content should not be empty"))
            }
        }
    }
}

