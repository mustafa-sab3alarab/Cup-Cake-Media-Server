package com.the_chance.controllers.validation

import com.the_chance.utils.InValidContentError

fun isValidContent(content: String?) {
    if (content.isNullOrEmpty()) throw InValidContentError
}