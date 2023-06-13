package com.the_chance.controllers.validation

import com.the_chance.utils.InValidPhoneNumber

fun isValidPhoneNumber(phoneNumber: String) {
    val phonePattern = Regex("^[+]?[0-9]{10,13}\$")
    if (!phonePattern.matches(phoneNumber)) throw InValidPhoneNumber()
}