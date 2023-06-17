package com.the_chance.plugins

import com.the_chance.data.authentication.TokenService
import com.the_chance.data.utils.ServerResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureAuthentication(tokenService: TokenService) {
    install(Authentication) {
        jwt("auth-jwt") {
            verifier(tokenService.jwtVerifier())
            validate { jwtCredential ->
                if (jwtCredential.payload.subject != null)
                    JWTPrincipal(jwtCredential.payload)
                else null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, ServerResponse("Token is not valid or has expired"))
            }
        }
    }
}