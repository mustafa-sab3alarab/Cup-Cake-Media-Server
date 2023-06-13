package com.the_chance.data.user

import com.the_chance.data.register.RegisterRequest
import com.the_chance.data.login.LoginRequest
import com.the_chance.data.utils.dbQuery
import com.the_chance.utils.LoginFailureError
import com.the_chance.utils.PhoneNumberExistError
import com.the_chance.utils.UserNameExistError
import com.the_chance.utils.md5
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction


class UserService(private val database: Database) {

    init {
        createUserTable()
    }

    private fun createUserTable() {
        transaction(database) {
            SchemaUtils.create(UserTable)
        }
    }

    suspend fun createUser(request: RegisterRequest): User {
        return dbQuery {
            val newUser = UserTable.insert {
                it[username] = request.username
                it[fullName] = request.fullName
                it[phoneNumber] = request.phoneNumber
                it[password] = request.password.md5()
            }

            User(
                id = newUser[UserTable.id].value.toString(),
                username = newUser[UserTable.username],
                fullName = newUser[UserTable.fullName],
                phoneNumber = newUser[UserTable.phoneNumber],
                isActive = newUser[UserTable.isActive],
                createdAt = newUser[UserTable.createdAt],
            )
        }
    }


    suspend fun validateUser(request: LoginRequest): User = dbQuery {
        UserTable.select { UserTable.username eq request.username }.singleOrNull()
            ?.takeIf { it[UserTable.password] == request.password.md5() }?.let { user ->
                User(
                    id = user[UserTable.id].value.toString(),
                    username = user[UserTable.username],
                    fullName = user[UserTable.fullName],
                    phoneNumber = user[UserTable.phoneNumber],
                    isActive = user[UserTable.isActive],
                    createdAt = user[UserTable.createdAt],
                )
            } ?: throw LoginFailureError()

    }

    suspend fun checkIfUserNameExist(username : String) = dbQuery {
        UserTable.select { UserTable.username eq username }.singleOrNull()?.let {
            throw UserNameExistError()
        }
    }


    suspend fun checkIfPhoneNumberExist(phoneNumber : String) = dbQuery {
        UserTable.select { UserTable.phoneNumber eq phoneNumber }.singleOrNull()?.let {
            throw PhoneNumberExistError()
        }
    }

}