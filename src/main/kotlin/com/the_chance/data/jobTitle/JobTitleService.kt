package com.the_chance.data.jobTitle


import com.the_chance.data.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class JobTitleService(database: Database) {

    init {
        transaction(database) {
            SchemaUtils.create(JobTitleTable)
        }
    }

   suspend fun getAllJobTitle():List<JobTitle> = dbQuery {
       JobTitleTable.selectAll().map {jobTitle ->
           JobTitle(
               jobTitle[JobTitleTable.id].value,
               jobTitle[JobTitleTable.title],
           )

       }
   }

    suspend fun insertJobTitle(jobTitle: String) = dbQuery {
        val newJobTitle = JobTitleTable.insert {
            it[title] = jobTitle
        }

        JobTitle(
            id = newJobTitle[JobTitleTable.id].value,
            title = newJobTitle[JobTitleTable.title]
        )
    }

    suspend fun checkIfJobTitleExist(id: Int): Boolean {
        return dbQuery {
            JobTitleTable.select { JobTitleTable.id eq id }.empty().not()
        }
    }


}