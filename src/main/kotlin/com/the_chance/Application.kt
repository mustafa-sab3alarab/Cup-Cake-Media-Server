package com.the_chance

import com.the_chance.data.getDataBase
import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.data.post.PostService
import com.the_chance.plugins.configureMonitoring
import com.the_chance.plugins.configureRouting
import com.the_chance.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    val database = getDataBase()

    val postService = PostService(database)
    val jobTitleService = JobTitleService(database)

    configureSerialization()
    configureMonitoring()
    configureRouting(postService, jobTitleService)
}
