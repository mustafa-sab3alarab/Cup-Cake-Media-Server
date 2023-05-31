package com.the_chance.data.jobTitle


import com.the_chance.data.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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
                findJobTitleByTitle(newTitle) ?: throw IllegalStateException("Failed to create job title"),
                false
            )
        }
    }

    //region search
    private suspend fun findJobTitleByTitle(title: String): JobTitle? {
        return searchJobTitle { JobTitleTable.title eq title }
    }
    suspend fun findJobTitleById(id: Int): JobTitle? {
        return searchJobTitle { JobTitleTable.id eq id }
    }

    private suspend fun searchJobTitle(where: SqlExpressionBuilder.() -> Op<Boolean>): JobTitle? {
        return dbQuery {
            JobTitleTable.select(where).singleOrNull()
                ?.let { jobTitle ->
                    JobTitle(
                        jobTitle[JobTitleTable.id].value,
                        jobTitle[JobTitleTable.title],
                    )
                }

        }
    }

    //endregion

   suspend fun getAllJobTitle():List<JobTitle> = dbQuery {
       JobTitleTable.selectAll().map {jobTitle ->
           JobTitle(
               jobTitle[JobTitleTable.id].value,
               jobTitle[JobTitleTable.title],
           )

       }
   }

    suspend fun deleteJobTitleById(id: Int): Boolean = dbQuery {
        JobTitleTable.deleteWhere { JobTitleTable.id eq id } > 0
    }

}