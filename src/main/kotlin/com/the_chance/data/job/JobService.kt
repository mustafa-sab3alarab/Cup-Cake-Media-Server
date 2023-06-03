package com.the_chance.data.job

import com.the_chance.data.jobTitle.JobTitle
import com.the_chance.data.jobTitle.JobTitleTable
import com.the_chance.data.utils.dbQuery
import com.the_chance.utils.DeleteError
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class JobService(database: Database) {

    init {
        transaction(database) {
            SchemaUtils.create(JobTable)
        }
    }

    suspend fun createJob(job: Job): Job = dbQuery {
        val newJob = JobTable.insert {
            it[this.jobTitleId] = job.jobTitleId
            it[this.company] = job.company
            it[this.workType] = job.workType
            it[this.jobLocation] = job.jobLocation
            it[this.jobType] = job.jobType
            it[this.jobDescription] = job.jobDescription
            it[this.salary] = job.jobSalary
        }
        Job(
            id = newJob[JobTable.id].value.toString(),
            jobTitleId = newJob[JobTable.jobTitleId].value,
            company = newJob[JobTable.company],
            createdAt = newJob[JobTable.createdAt],
            workType = newJob[JobTable.workType],
            jobLocation = newJob[JobTable.jobLocation],
            jobType = newJob[JobTable.jobType],
            jobDescription = newJob[JobTable.jobDescription],
            jobSalary = newJob[JobTable.salary]
        )
    }

    suspend fun getAllJobs(): List<JobWithJobTitle> {
        return dbQuery {
            (JobTable innerJoin JobTitleTable)
                .slice(JobTable.columns + JobTitleTable.columns)
                .selectAll()
                .map {
                    JobWithJobTitle(
                        id = it[JobTable.id].value.toString(),
                        jobTitle = JobTitle(
                            id = it[JobTitleTable.id].value,
                            title = it[JobTitleTable.title]
                        ),
                        company = it[JobTable.company],
                        createdAt = it[JobTable.createdAt],
                        workType = it[JobTable.workType],
                        jobLocation = it[JobTable.jobLocation],
                        jobType = it[JobTable.jobType],
                        jobDescription = it[JobTable.jobDescription],
                        jobSalary = it[JobTable.salary]
                    )
                }
        }
    }

    suspend fun getJobById(jobId: UUID): Job? {
        return dbQuery {
                JobTable.select { JobTable.id eq jobId }.singleOrNull()
                    ?.let { job ->
                        Job(
                            id = job[JobTable.id].value.toString(),
                            jobTitleId = job[JobTable.jobTitleId].value,
                            company = job[JobTable.company],
                            createdAt = job[JobTable.createdAt],
                            workType = job[JobTable.workType],
                            jobLocation = job[JobTable.jobLocation],
                            jobType = job[JobTable.jobType],
                            jobDescription = job[JobTable.jobDescription],
                            jobSalary = job[JobTable.salary]
                        )
                    }


        }
    }

    suspend fun deleteJob(jobId: UUID) {
         return dbQuery {
             val deleteResult = JobTable.deleteWhere { JobTable.id eq jobId }
             if(deleteResult != 1) {
                 throw DeleteError()
             }
        }
    }
}