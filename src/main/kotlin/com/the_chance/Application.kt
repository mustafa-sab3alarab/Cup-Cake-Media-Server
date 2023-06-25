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
import com.the_chance.data.profle.education.EducationService
import com.the_chance.data.profle.skills.SkillService
import com.the_chance.data.user.UserService
import com.the_chance.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    val database by lazy { AppDatabase }
    database.getDataBase()

    val imageService by lazy { ImageService() }
    val imageController by lazy { ImageController(imageService) }

    val postService by lazy { PostService(imageService) }
    val postsController by lazy { PostsController(postService) }

    val jobTitleService by lazy { JobTitleService() }
    val jobTitleController by lazy { JobTitleController(jobTitleService) }

    val jobService by lazy { JobService() }
    val jobController by lazy { JobController(jobService, jobTitleService) }

    val profileService by lazy { ProfileService(jobTitleService) }
    val tokenService by lazy { TokenService() }
    val userService by lazy { UserService(profileService) }
    val authenticationController by lazy { AuthenticationController(userService, jobTitleService, tokenService) }


    val adminController by lazy { AdminController(database) }

    val commentService by lazy { CommentService() }
    val commentController by lazy { CommentController(commentService) }

    val educationService by lazy { EducationService() }
    val skillService by lazy { SkillService() }
    val profileController by lazy { ProfileController(educationService, skillService) }

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
        commentController,
        profileController
    )
}
