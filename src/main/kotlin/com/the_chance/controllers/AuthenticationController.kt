package com.the_chance.controllers

import com.the_chance.controllers.validation.*
import com.the_chance.data.authentication.TokenService
import com.the_chance.data.login.CredentialsBaseAuth
import com.the_chance.data.register.UserWithToken
import com.the_chance.data.register.RegisterRequest
import com.the_chance.data.user.UserService
import com.the_chance.controllers.mappers.toUserWithToken
import com.the_chance.data.authentication.Token
import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.utils.BASIC_AUTHORIZATION
import com.the_chance.utils.COLON
import com.the_chance.utils.InValidBaseAuthorizationHeader
import com.the_chance.utils.NoJobTitleFoundError
import java.util.*

class AuthenticationController(
    private val userService: UserService,
    private val jobTitleService: JobTitleService,
    private val tokenService: TokenService
) {

    suspend fun createUser(user: RegisterRequest): UserWithToken {
        registerValidateField(user)
        userService.checkIfUserNameExist(user.username)
        userService.checkIfEmailExist(user.email)
        isJobTitleExist(user.jobTitleId)
        return userService.createUser(user).let { newUser ->
            newUser.toUserWithToken(generateToken(newUser.id))
        }

    }

    suspend fun login(authorizationHeader: String?): UserWithToken {
        validateAuthorizationHeader(authorizationHeader)
        return extractCredentialsFromAuthorizationHeader(authorizationHeader!!).let { credentials ->
            credentialsValidateField(credentials)
            userService.validateUser(credentials).let { newUser ->
                newUser.toUserWithToken(generateToken(newUser.id))
            }
        }

    }

    private fun extractCredentialsFromAuthorizationHeader(authorizationHeader: String): CredentialsBaseAuth {
        val encodedCredentials = authorizationHeader.removePrefix(BASIC_AUTHORIZATION)
        val decodedCredentials = String(Base64.getDecoder().decode(encodedCredentials))
        val credentials = decodedCredentials.split(COLON)

        return credentials.takeIf { it.size == 2 }?.let {
            CredentialsBaseAuth(
                credentials.first().trim(),
                credentials.last().trim(),
            )
        } ?: throw InValidBaseAuthorizationHeader

    }

    private fun generateToken(id: String): Token {
        return tokenService.generateJwt(id)
    }

    private suspend fun isJobTitleExist(jobTitleId: Int): Boolean {
        return if (jobTitleService.checkIfJobTitleExist(jobTitleId)) true else throw NoJobTitleFoundError()
    }

}