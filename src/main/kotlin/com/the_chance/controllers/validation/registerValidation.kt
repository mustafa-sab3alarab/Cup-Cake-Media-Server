package com.the_chance.controllers.validation

import com.the_chance.data.register.RegisterRequest

fun registerValidateField(user: RegisterRequest) {
    validateUserRequest(user)
    usernameValidation(user.username)
    passwordValidation(user.password)
    isValidPhoneNumber(user.phoneNumber)
}