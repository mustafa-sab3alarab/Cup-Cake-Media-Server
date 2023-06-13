package com.the_chance.utils.errors.auth

import com.the_chance.utils.PasswordIsRequiredError
import com.the_chance.utils.UsernameIsRequiredError
import com.the_chance.utils.badRequest
import io.ktor.server.plugins.statuspages.*

fun StatusPagesConfig.authErrorsException() {

    exception<PasswordIsRequiredError> { call, _ ->
        call.badRequest("Password is required.")
    }

    exception<UsernameIsRequiredError> { call, _ ->
        call.badRequest("Username is required.")
    }

}