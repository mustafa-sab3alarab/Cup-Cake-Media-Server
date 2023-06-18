package com.the_chance.controllers

import com.the_chance.controllers.validation.*
import com.the_chance.data.authentication.TokenService
import com.the_chance.data.login.LoginRequest
import com.the_chance.data.register.UserWithToken
import com.the_chance.data.register.RegisterRequest
import com.the_chance.data.user.UserService
import com.the_chance.controllers.mappers.toUserWithToken
import com.the_chance.data.authentication.Token

class AuthenticationController(private val userService: UserService, private val tokenService: TokenService) {

    suspend fun createUser(user: RegisterRequest): UserWithToken {
        registerValidateField(user)
        userService.checkIfUserNameExist(user.username)
        userService.checkIfPhoneNumberExist(user.phoneNumber)
        return userService.createUser(user).let { newUser ->
            newUser.toUserWithToken(generateToken(newUser.id))
        }

    }

    suspend fun login(loginRequest: LoginRequest): Token {
        loginValidateField(loginRequest)
        return generateToken(userService.validateUser(loginRequest).id)
    }

    private fun generateToken(id: String): Token {
        return tokenService.generateJwt(id)
    }


}