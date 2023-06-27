package com.the_chance.data.jobTitle


import com.the_chance.data.post.PostTable
import com.the_chance.data.utils.dbQuery
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll


class JobTitleService {

    suspend fun getAllJobTitle(): List<JobTitle> = dbQuery {
        JobTitleTable.selectAll()
            .map { jobTitle ->
                JobTitle(
                    id = jobTitle[JobTitleTable.id].value,
                    title = jobTitle[JobTitleTable.title]
                )
            }
    }

    suspend fun insertJobTitle(jobTitle: String) = dbQuery {
        JobTitleTable.insert {
            it[title] = jobTitle
        }
    }

    suspend fun checkIfJobTitleExist(id: Int): Boolean {
        return dbQuery {
            JobTitleTable.select { JobTitleTable.id eq id }.empty().not()
        }
    }

    suspend fun findJobTitleById(id: Int): JobTitle? {
        return dbQuery {
            JobTitleTable.select { JobTitleTable.id eq id }.singleOrNull()
                ?.let { jobTitle ->
                    JobTitle(
                        jobTitle[JobTitleTable.id].value,
                        jobTitle[JobTitleTable.title],
                    )
                }

        }
    }

    suspend fun checkIfJobTitleTableIsEmpty(): Boolean {
        return dbQuery {
            JobTitleTable.selectAll().empty()
        }
    }

}