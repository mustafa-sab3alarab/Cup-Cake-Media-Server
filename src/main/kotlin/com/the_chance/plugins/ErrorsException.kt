package com.the_chance.plugins

import com.the_chance.utils.errors.auth.authErrorsException
import com.the_chance.utils.errors.genericErrorsException
import com.the_chance.utils.errors.image.imageErrorsException
import com.the_chance.utils.errors.login.loginErrorsException
import com.the_chance.utils.errors.profile.profileErrorsException
import com.the_chance.utils.errors.register.registerErrorsException
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*


fun Application.configureErrorsException() {

    install(StatusPages) {

        authErrorsException()
        loginErrorsException()
        registerErrorsException()
        imageErrorsException()
        genericErrorsException()
        profileErrorsException()
    }

}
