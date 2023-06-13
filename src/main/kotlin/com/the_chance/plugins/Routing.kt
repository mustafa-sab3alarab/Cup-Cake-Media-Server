package com.the_chance.plugins


import com.the_chance.controllers.*
import com.the_chance.endpoints.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    postsController: PostsController,
    jobController: JobController,
    jobTitleController: JobTitleController,
    authenticationController: AuthenticationController
) {
    routing {
        swaggerUI(path = "swagger")
        postsRoutes(postsController)
        jobTitleRoute(jobTitleController)
        jobRoutes(jobController)
        authentication(authenticationController)
    }
}
