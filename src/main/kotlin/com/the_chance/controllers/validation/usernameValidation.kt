package com.the_chance.controllers.validation

import com.the_chance.utils.InValidUsername
import com.the_chance.utils.UsernameIsLongError
import com.the_chance.utils.UsernameIsRequiredError
import com.the_chance.utils.UsernameIsShortError


fun usernameValidation(username: String?) {
    val regex = Regex("^[a-zA-Z\\-]+$")
    if (username.isNullOrBlank()) throw UsernameIsRequiredError()
    else if (username.length < 6) throw UsernameIsShortError()
    else if (username.length > 250) throw UsernameIsLongError()
    else if (!regex.matches(username)) throw InValidUsername()

}
