package com.the_chance.data.profle

import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.data.utils.dbQuery
import com.the_chance.utils.InValidJobTitleIdError
import org.jetbrains.exposed.sql.insert

class ProfileService(private val jobTitleService: JobTitleService) {


    suspend fun createProfile(jobTitleId: Int): Profile = dbQuery {
        val jobTitle = jobTitleService.findJobTitleById(jobTitleId)
            ?: throw InValidJobTitleIdError

        ProfileTable.insert {
            it[ProfileTable.jobTitleId] = jobTitleId
        }.let { profile ->
            Profile(
                id = profile[ProfileTable.id].value.toString(),
                bio = profile[ProfileTable.bio],
                avatar = profile[ProfileTable.avatar],
                linkWebsite = profile[ProfileTable.linkWebsite],
                location = profile[ProfileTable.location],
                jobTitle = jobTitle
            )
        }

    }


}