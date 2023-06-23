package com.the_chance.utils.errors.register

import com.the_chance.utils.*
import io.ktor.server.plugins.statuspages.*

fun StatusPagesConfig.registerErrorsException(){

    //region Password
    exception<PasswordIsShortError> { call, _ ->
        call.badRequest("Password is too short. It should be a minimum of 8 characters.")
    }

    exception<PasswordIsLongError> { call, _ ->
        call.badRequest("Password is too long. It should be a maximum of 250 characters.")
    }

    exception<InValidPassword> { call, _ ->
        call.badRequest("Password should only contain alphabets, numbers, dashes, and at least one of the special characters !, @, $, %, *, or &.")
    }

    exception<PasswordNotStrongError> { call, _ ->
        call.badRequest("Password not strong, Must include at least one of the special characters !, @, $, %, *, or &.")
    }
    //endregion


    //region Username

    exception<UsernameIsShortError> { call, _ ->
        call.badRequest("Username is too short. It should be a minimum of 6 characters.")
    }

    exception<UsernameIsLongError> { call, _ ->
        call.badRequest("Username is too long. It should be a maximum of 250 characters.")
    }

    exception<InValidUsername> { call, _ ->
        call.badRequest( "Username should only contain alphabets and dashes.")
    }

    exception<UserNameExistError> { call, _ ->
        call.badRequest( "Username already exists.")
    }

    //endregion


    //region Email
    exception<EmailIsRequiredError> { call, _ ->
        call.badRequest("Email is required.")
    }

    exception<EmailExistError> { call, _ ->
        call.badRequest("Email already exists.")
    }

    exception<InValidEmail> { call, _ ->
        call.badRequest("Email not valid")
    }
    //endregion

    exception<FullNameIsRequiredError> { call, _ ->
        call.badRequest("Full name is required.")
    }

    exception<InValidJobTitleIdError> { call, _ ->
        call.badRequest("Invalid job title ID.")
    }

}