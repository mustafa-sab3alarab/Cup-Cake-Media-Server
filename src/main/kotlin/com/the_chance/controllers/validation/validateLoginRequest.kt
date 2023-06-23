package com.the_chance.controllers.validation

import com.the_chance.data.login.CredentialsBaseAuth
import com.the_chance.utils.PasswordIsRequiredError
import com.the_chance.utils.UsernameIsRequiredError

fun credentialsValidateField(credential: CredentialsBaseAuth) {

    if (credential.username.isEmpty()) throw UsernameIsRequiredError()
    else if (credential.password.isEmpty()) throw PasswordIsRequiredError()

}