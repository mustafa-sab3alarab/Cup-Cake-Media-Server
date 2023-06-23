package com.the_chance.controllers.validation

import com.the_chance.utils.BASIC_AUTHORIZATION
import com.the_chance.utils.InValidBaseAuthorizationHeader

fun validateAuthorizationHeader(authorizationHeader: String?) {
    if (authorizationHeader == null || !authorizationHeader.startsWith(BASIC_AUTHORIZATION)) {
        throw InValidBaseAuthorizationHeader
    }
}