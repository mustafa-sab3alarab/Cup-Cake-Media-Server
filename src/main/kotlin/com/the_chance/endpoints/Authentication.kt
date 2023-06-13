package com.the_chance.endpoints

import com.the_chance.controllers.AuthenticationController
import com.the_chance.data.login.LoginRequest
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
        tryQuery {
            val params = call.receiveParameters()
            val loginRequest = LoginRequest(
                params[USERNAME]?.trim().orEmpty(),
                params[PASSWORD]?.trim().orEmpty(),
            )
            val login = authentication.login(loginRequest)
            call.respond(HttpStatusCode.OK, ServerResponse.success(login))
        }
    }

    post("/register") {

        tryQuery {
            val params = call.receiveParameters()
            val user = RegisterRequest(
                username = params[USERNAME]?.trim().orEmpty(),
                fullName = params[FULL_NAME]?.trim().orEmpty(),
                phoneNumber = params[PHONE_NUMBER]?.trim().orEmpty(),
                password = params[PASSWORD]?.trim().orEmpty(),
            )

            val newUser = authentication.createUser(user)
            call.respond(HttpStatusCode.OK, ServerResponse.success(newUser))
        }
    }
}