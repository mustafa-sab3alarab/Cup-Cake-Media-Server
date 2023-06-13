package com.the_chance.controllers.validation

import com.the_chance.data.login.LoginRequest
import com.the_chance.utils.PasswordIsRequiredError
import com.the_chance.utils.UsernameIsRequiredError

fun loginValidateField(loginRequest: LoginRequest) {

    if (loginRequest.username.isEmpty()) throw UsernameIsRequiredError()
    else if (loginRequest.password.isEmpty()) throw PasswordIsRequiredError()

}