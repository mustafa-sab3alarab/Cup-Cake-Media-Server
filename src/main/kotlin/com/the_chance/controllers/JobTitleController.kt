package com.the_chance.controllers

import com.the_chance.data.jobTitle.JobTitle
import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.utils.NoJobTitlesFoundError

class JobTitleController(private val jobTitleService: JobTitleService) {

    suspend fun getAllJobTitles(): List<JobTitle> {
        return jobTitleService.getAllJobTitle().ifEmpty {
            throw NoJobTitlesFoundError()
        }
    }

    //todo this is a temporary solution and should be removed in the future
    suspend fun createJobTitle(jobTitle: String): JobTitle {
        return jobTitleService.insertJobTitle(jobTitle)
    }
}