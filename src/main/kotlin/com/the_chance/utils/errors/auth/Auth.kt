package com.the_chance.utils.errors.auth

import com.the_chance.data.utils.ServerResponse
import com.the_chance.utils.PasswordIsRequiredError
import com.the_chance.utils.Unauthorized
import com.the_chance.utils.UsernameIsRequiredError
import com.the_chance.utils.badRequest
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.authErrorsException() {

    exception<PasswordIsRequiredError> { call, _ ->
        call.badRequest("Password is required.")
    }

    exception<UsernameIsRequiredError> { call, _ ->
        call.badRequest("Username is required.")
    }

    exception<Unauthorized> { call, _ ->
        call.respond(HttpStatusCode.Unauthorized, ServerResponse.error("Unauthorized"))
    }

}