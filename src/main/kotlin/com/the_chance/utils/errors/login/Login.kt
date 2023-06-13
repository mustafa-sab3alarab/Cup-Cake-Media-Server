package com.the_chance.utils.errors.login

import com.the_chance.utils.*
import io.ktor.server.plugins.statuspages.*

fun StatusPagesConfig.loginErrorsException() {

    exception<LoginFailureError> { call, _ ->
        call.badRequest("Invalid username or password")
    }

}