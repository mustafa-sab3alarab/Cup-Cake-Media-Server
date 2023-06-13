package com.the_chance

import com.the_chance.controllers.JobController
import com.the_chance.controllers.JobTitleController
import com.the_chance.data.authentication.TokenService
import com.the_chance.controllers.PostsController
import com.the_chance.controllers.*
import com.the_chance.data.getDataBase
import com.the_chance.data.job.JobService
import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.data.post.PostService
import com.the_chance.data.user.UserService
import com.the_chance.plugins.*
import com.the_chance.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    val database = getDataBase()

    val postService = PostService(database)
    val postsController = PostsController(postService)

    val jobTitleService = JobTitleService(database)
    val jobTitleController = JobTitleController(jobTitleService)

    val jobService = JobService(database)
    val jobController = JobController(jobService, jobTitleService)

    val tokenService = TokenService()
    val userService = UserService(database)
    val authenticationController = AuthenticationController(userService, tokenService)


    configureAuthentication(tokenService)
    configureSerialization()
    configureMonitoring()
    configureErrorsException()
    configureRouting(postsController, jobController, jobTitleController, authenticationController)
}
