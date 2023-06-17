package com.the_chance.utils


class InValidContentError: Throwable()

class NoPostFoundError: Throwable()

class InValidIdError: Throwable()

class InValidJobTitleIdError: Throwable()

class NoJobTitleFoundError: Throwable()

class InValidJobError: Throwable()

class InValidSalaryError: Throwable()

class NoJobFoundError: Throwable()

class DeleteError: Throwable()

//region register

//region password
class PasswordIsRequiredError : Throwable()
class PasswordIsShortError : Throwable()
class PasswordIsLongError : Throwable()
class InValidPassword: Throwable()
class PasswordNotStrongError : Throwable()
//endregion

//region Username
class UsernameIsRequiredError : Throwable()
class UsernameIsShortError : Throwable()
class UsernameIsLongError : Throwable()
class InValidUsername : Throwable()

class UserNameExistError : Throwable()
//endregion

class FullNameIsRequiredError : Throwable()
class PhoneNumberIsRequiredError : Throwable()
class InValidPhoneNumber : Throwable()

class PhoneNumberExistError : Throwable()
class NoJobTitleFoundRegisterError(): Throwable()
//endregion

class LoginFailureError : Throwable()

object Unauthorized : Throwable()