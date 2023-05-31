package com.the_chance.data.jobTitle


import com.the_chance.data.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class JobTitleService(private val database: Database) {

    init {
        transaction(database) {
            SchemaUtils.create(JobTitleTable)
        }
    }


    suspend fun createJobTitle(newTitle: String): Pair<JobTitle, Boolean> = dbQuery {
        try {
            JobTitleTable.insertIgnore { it[title] = newTitle }.let { newJobTitle ->
                JobTitle(
                    newJobTitle[JobTitleTable.id].value,
                    newJobTitle[JobTitleTable.title]
                )
            }.let { Pair(it, true) }
        } catch (e: Exception) {
            Pair(
                findJobTitle(newTitle) ?: throw IllegalStateException("Failed to create job title"),
                false
            )
        }
    }

    private suspend fun findJobTitle(title: String): JobTitle? {
        return dbQuery {
            JobTitleTable.select { JobTitleTable.title eq title }.singleOrNull()
                    ?.let { jobTitle ->
                        JobTitle(
                                jobTitle[JobTitleTable.id].value,
                                jobTitle[JobTitleTable.title],
                        )
                    }

        }
    }


}