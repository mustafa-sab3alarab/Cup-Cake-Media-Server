package com.the_chance.controllers.validation

import com.the_chance.data.register.RegisterRequest
import com.the_chance.utils.FullNameIsRequiredError
import com.the_chance.utils.PasswordIsRequiredError
import com.the_chance.utils.EmailIsRequiredError
import com.the_chance.utils.UsernameIsRequiredError

fun validateUserRequest(userRequest: RegisterRequest) {
    if (userRequest.username.isEmpty()) throw UsernameIsRequiredError()
    else if (userRequest.fullName.isEmpty()) throw FullNameIsRequiredError()
    else if (userRequest.email.isEmpty()) throw EmailIsRequiredError()
    else if (userRequest.password.isEmpty()) throw PasswordIsRequiredError()

}