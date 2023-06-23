package com.the_chance.endpoints

import com.the_chance.controllers.AuthenticationController
import com.the_chance.data.register.RegisterRequest
import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.utils.tryQuery
import com.the_chance.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.authentication(authentication: AuthenticationController) {

    get("/login") {

        val authorizationHeader = call.request.header(HttpHeaders.Authorization)
        val login = authentication.login(authorizationHeader)
        call.respond(HttpStatusCode.OK, ServerResponse.success(login))

    }

    post("/register") {

        tryQuery {
            val params = call.receiveParameters()
            val user = RegisterRequest(
                username = params[USERNAME]?.trim().orEmpty(),
                fullName = params[FULL_NAME]?.trim().orEmpty(),
                email = params[EMAIL]?.trim().orEmpty(),
                password = params[PASSWORD]?.trim().orEmpty(),
                jobTitleId = params[JOB_TITLE_ID]?.trim()?.toIntOrNull() ?: -1
            )

            val newUser = authentication.createUser(user)
            call.respond(HttpStatusCode.OK, ServerResponse.success(newUser))
        }
    }
}