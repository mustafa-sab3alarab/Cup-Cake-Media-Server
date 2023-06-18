package com.the_chance.controllers.validation

import com.the_chance.utils.*


fun passwordValidation(password: String?) {
    val regex = Regex("^[a-zA-Z\\d!@\$%*&\\-]+$")
    if (password.isNullOrBlank()) throw PasswordIsRequiredError()
    else if (password.length < 8) throw PasswordIsShortError()
    else if (password.length > 250) throw PasswordIsLongError()
    else if (!regex.matches(password)) throw InValidPassword()
    else if (!password.contains(Regex("[!@\$%*&]"))) throw PasswordNotStrongError()

}

