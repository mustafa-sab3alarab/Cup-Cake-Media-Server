package com.the_chance.plugins


import com.the_chance.controllers.PostsController
import com.the_chance.data.job.JobService
import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.jobRoutes
import com.the_chance.endpoints.jobTitleRoute
import com.the_chance.endpoints.postsRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    postsController: PostsController,
    jobService: JobService,
    jobTitleService: JobTitleService
) {
    routing {
        get("/") {
            call.respond(ServerResponse.success("Welcome to Cup Cake Media"))
        }
        postsRoutes(postsController)
        jobTitleRoute(jobTitleService)
        jobRoutes(jobService,jobTitleService)
    }
}
