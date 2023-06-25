package com.the_chance.utils.errors.profile

import com.the_chance.utils.EducationFieldsError
import com.the_chance.utils.SkillFieldsError
import com.the_chance.utils.badRequest
import io.ktor.server.plugins.statuspages.*

fun StatusPagesConfig.profileErrorsException() {

    exception<EducationFieldsError> { call, _ ->
        call.badRequest("All fields are required.")
    }

    exception<SkillFieldsError> { call, _ ->
        call.badRequest("Skill field is required.")
    }
}