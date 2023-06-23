package com.the_chance

import com.the_chance.controllers.*
import com.the_chance.data.AppDatabase
import com.the_chance.data.authentication.TokenService
import com.the_chance.data.comment.CommentService
import com.the_chance.data.image.ImageService
import com.the_chance.data.job.JobService
import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.data.post.PostService
import com.the_chance.data.profle.ProfileService
import com.the_chance.data.user.UserService
import com.the_chance.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    val database = AppDatabase
    database.getDataBase()

    val imageService = ImageService()
    val imageController = ImageController(imageService)

    val postService = PostService(imageService)
    val postsController = PostsController(postService)

    val jobTitleService = JobTitleService()
    val jobTitleController = JobTitleController(jobTitleService)

    val jobService = JobService()
    val jobController = JobController(jobService, jobTitleService)

    val profileService = ProfileService(jobTitleService)
    val tokenService = TokenService()
    val userService = UserService(profileService)
    val authenticationController = AuthenticationController(userService, jobTitleService, tokenService)




    val adminController = AdminController(database)

    val commentService = CommentService()
    val commentController = CommentController(commentService)

    configureAuthentication(tokenService)
    configureSerialization()
    configureMonitoring()
    configureErrorsException()
    configureRouting(
        postsController,
        jobController,
        jobTitleController,
        imageController,
        authenticationController,
        adminController,
        commentController
    )
}
