package com.the_chance.utils


object InValidContentError: Throwable()

class NoPostFoundError: Throwable()

class InValidIdError: Throwable()

object InValidJobTitleIdError: Throwable()

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
class EmailIsRequiredError : Throwable()
class InValidEmail : Throwable()

class EmailExistError : Throwable()
class NoJobTitleFoundRegisterError(): Throwable()
//endregion

class LoginFailureError : Throwable()

object Unauthorized : Throwable()
object CommentNotFoundError : Throwable()
object NotUpdateCommentError : Throwable()
object UserNotFoundError : Throwable()

object IllegalImage : Throwable()
object FailedImageDeleteError : Throwable()
object FileNotExistError : Throwable()
object InValidBaseAuthorizationHeader : Throwable()