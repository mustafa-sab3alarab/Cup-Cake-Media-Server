package com.the_chance.plugins


import com.the_chance.controllers.JobController
import com.the_chance.controllers.JobTitleController
import com.the_chance.controllers.PostsController
import com.the_chance.endpoints.jobRoutes
import com.the_chance.endpoints.jobTitleRoute
import com.the_chance.endpoints.postsRoutes
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    postsController: PostsController,
    jobController: JobController,
    jobTitleController: JobTitleController
) {
    routing {
        swaggerUI(path = "swagger")
        postsRoutes(postsController)
        jobTitleRoute(jobTitleController)
        jobRoutes(jobController)
    }
}
