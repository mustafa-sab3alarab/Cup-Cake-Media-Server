package com.the_chance.controllers.validation

import com.the_chance.utils.InValidIdError
import java.util.*


fun isValidUUID(uuid: String?): UUID {
    return try {
        UUID.fromString(uuid)
    } catch (e: IllegalArgumentException) {
        throw InValidIdError()
    }
}