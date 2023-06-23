package com.the_chance.data.user

import com.the_chance.controllers.validation.isValidUUID
import com.the_chance.data.jobTitle.JobTitle
import com.the_chance.data.jobTitle.JobTitleTable
import com.the_chance.data.login.CredentialsBaseAuth
import com.the_chance.data.profle.Profile
import com.the_chance.data.profle.ProfileService
import com.the_chance.data.profle.ProfileTable
import com.the_chance.data.register.RegisterRequest
import com.the_chance.data.utils.dbQuery
import com.the_chance.utils.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.UUID


class UserService(private val profileService: ProfileService) {

    suspend fun createUser(request: RegisterRequest): User {
        return dbQuery {
            val profile = profileService.createProfile(1)
            val newUser = UserTable.insert {
                it[username] = request.username
                it[fullName] = request.fullName
                it[email] = request.email
                it[password] = request.password.md5()
                it[profileId] = isValidUUID(profile.id)
            }

            User(
                id = newUser[UserTable.id].value.toString(),
                username = newUser[UserTable.username],
                fullName = newUser[UserTable.fullName],
                email = newUser[UserTable.email],
                isActive = newUser[UserTable.isActive],
                createdAt = newUser[UserTable.createdAt].toString(),
                profile = profile
            )
        }
    }


    suspend fun validateUser(request: CredentialsBaseAuth): User = dbQuery {
        UserTable.select { UserTable.username eq request.username }.singleOrNull()
            ?.takeIf { it[UserTable.password] == request.password.md5() }?.let { user ->
                getUserById(user[UserTable.id].value)
            } ?: throw LoginFailureError()

    }

    suspend fun getUserById(userId: UUID): User = dbQuery {
        (UserTable innerJoin ProfileTable innerJoin JobTitleTable)
            .slice(UserTable.columns + ProfileTable.columns + JobTitleTable.columns).select { UserTable.id eq userId }
            .singleOrNull()?.let { user ->
                User(
                    id = user[UserTable.id].value.toString(),
                    username = user[UserTable.username],
                    fullName = user[UserTable.fullName],
                    email = user[UserTable.email],
                    isActive = user[UserTable.isActive],
                    createdAt = user[UserTable.createdAt].toString(),
                    profile = Profile(
                        id = user[ProfileTable.id].value.toString(),
                        bio = user[ProfileTable.bio],
                        avatar = user[ProfileTable.avatar],
                        linkWebsite = user[ProfileTable.linkWebsite],
                        location = user[ProfileTable.location],
                        jobTitle = JobTitle(
                            user[JobTitleTable.id].value,
                            user[JobTitleTable.title],
                        )
                    )
                )
            } ?: throw UserNotFoundError

    }

    suspend fun checkIfUserNameExist(username: String) = dbQuery {
        UserTable.select { UserTable.username eq username }.singleOrNull()?.let {
            throw UserNameExistError()
        }
    }


    suspend fun checkIfEmailExist(email: String) = dbQuery {
        UserTable.select { UserTable.email eq email }.singleOrNull()?.let {
            throw EmailExistError()
        }
    }

}