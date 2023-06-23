package com.the_chance.utils.errors.login

import com.the_chance.utils.*
import io.ktor.server.plugins.statuspages.*

fun StatusPagesConfig.loginErrorsException() {

    exception<LoginFailureError> { call, _ ->
        call.badRequest("Invalid username or password")
    }
    exception<UserNotFoundError> { call, _ ->
        call.badRequest("User not found")
    }

    exception<InValidBaseAuthorizationHeader> { call, _ ->
        call.badRequest("InValid base authorization header.")
    }

}