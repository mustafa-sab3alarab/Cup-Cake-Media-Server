package com.the_chance.controllers

import com.the_chance.data.jobTitle.JobTitle
import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.data.jobTitle.JobTitles
import com.the_chance.data.utils.dbQuery
import com.the_chance.utils.JobTitlesAlreadyExist
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

class JobTitleController(private val jobTitleService: JobTitleService) {

    suspend fun getAllJobTitles(): List<JobTitle> {
        return jobTitleService.getAllJobTitle()
    }

    suspend fun parseJobTitlesJsonFile() {
        dbQuery {
            jobTitleService.checkIfJobTitleTableIsEmpty().takeIf { it }?.let {
                val jsonFile = File("src/main/resources/job.json")
                val jsonString = jsonFile.readText()

                val titles = Json.decodeFromString<JobTitles>(jsonString)

                titles.results
                    .sortedBy { it.title }
                    .map { jobTitleService.insertJobTitle(it.title) }
            } ?: throw JobTitlesAlreadyExist
        }
    }
}