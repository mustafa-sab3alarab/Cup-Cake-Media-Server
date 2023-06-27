package com.the_chance.endpoints

import com.the_chance.controllers.JobTitleController
import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.utils.tryQuery
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.jobTitleRoute(jobTitleController: JobTitleController) {


    get("/jobTitles") {
        tryQuery {
            val jobTitles = jobTitleController.getAllJobTitles()
            call.respond(HttpStatusCode.OK, ServerResponse.success(jobTitles))
        }
    }

    post("/jobTitle") {
        tryQuery {
            jobTitleController.parseJobTitlesJsonFile()
            call.respond(HttpStatusCode.OK, ServerResponse.success(Unit, "Done"))
        }
    }
}

