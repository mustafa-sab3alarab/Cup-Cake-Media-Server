package com.the_chance

import com.the_chance.controllers.JobController
import com.the_chance.controllers.JobTitleController
import com.the_chance.data.authentication.TokenService
import com.the_chance.controllers.PostsController
import com.the_chance.controllers.*
import com.the_chance.data.AppDatabase
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

    val postService = PostService()
    val postsController = PostsController(postService)

    val jobTitleService = JobTitleService()
    val jobTitleController = JobTitleController(jobTitleService)

    val jobService = JobService()
    val jobController = JobController(jobService, jobTitleService)

    val tokenService = TokenService()
    val userService = UserService()
    val authenticationController = AuthenticationController(userService, tokenService)

    val database = AppDatabase
    database.getDataBase()

    configureAuthentication(tokenService)
    configureSerialization()
    configureMonitoring()
    configureErrorsException()
    configureRouting(postsController, jobController, jobTitleController, authenticationController, database)
}
