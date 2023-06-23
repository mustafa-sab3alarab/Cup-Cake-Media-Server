package com.the_chance.controllers.validation

import com.the_chance.utils.InValidEmail


fun isEmailValid(email: String) {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
    if (!email.matches(emailRegex)) throw InValidEmail()
}