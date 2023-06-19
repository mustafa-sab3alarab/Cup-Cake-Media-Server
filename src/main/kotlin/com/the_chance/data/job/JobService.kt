package com.the_chance.data.job

import com.the_chance.data.jobTitle.JobTitle
import com.the_chance.data.jobTitle.JobTitleTable
import com.the_chance.data.utils.dbQuery
import com.the_chance.utils.DeleteError
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class JobService {

    // region user job
    suspend fun createJob(userUUID: UUID, job: Job) = dbQuery {
        JobTable.insert {
            it[this.jobTitleId] = job.jobTitle.id
            it[this.creatorId] = userUUID
            it[this.company] = job.company
            it[this.workType] = job.workType
            it[this.jobLocation] = job.jobLocation
            it[this.jobType] = job.jobType
            it[this.jobDescription] = job.jobDescription
            it[this.minSalary] = job.jobSalary.minSalary
            it[this.maxSalary] = job.jobSalary.maxSalary
            it[this.experience] = job.experience
            it[this.education] = job.education
        }
    }

    suspend fun getAllUserJobs(userId: UUID): List<Job> {
        return dbQuery {
            (JobTable innerJoin JobTitleTable)
                .slice(JobTable.columns + JobTitleTable.columns)
                .select { JobTable.creatorId eq userId }
                .map { mapJobFromResultRow(it) }
        }
    }


    suspend fun getRecommendedJobs(userUUID: UUID, limit: Int): List<Job> {
        // TODO(" we should have job title in user table)
        // we can get user job title from payload
        return dbQuery { emptyList() }
    }

    suspend fun getTopSalaryJobsInLocation(userUUID: UUID, limit: Int): List<Job> {
        // TODO(" we should have user location)
        return dbQuery { emptyList() }
    }

    suspend fun getJobsInLocation(userUUID: UUID, limit: Int): List<Job> {
        // TODO(" we should have user location)
        return dbQuery { emptyList() }
    }

    suspend fun deleteJob(jobId: UUID) {
        return dbQuery {
            val deleteResult = JobTable.deleteWhere { (JobTable.id eq jobId) }
            println("Mustafa $deleteResult")
            if (deleteResult != 1) {
                throw DeleteError()
            }
        }
    }

    //endregion

    //region public job

    suspend fun getJobById(jobId: UUID): Job? {
        return dbQuery {
            (JobTable innerJoin JobTitleTable)
                .select { JobTable.id eq jobId }.singleOrNull()
                ?.let { job ->
                    Job(
                        id = job[JobTable.id].value.toString(),
                        jobTitle = JobTitle(
                            job[JobTitleTable.id].value,
                            job[JobTitleTable.title]
                        ),
                        creatorId = job[JobTable.creatorId].value.toString(),
                        company = job[JobTable.company],
                        createdAt = job[JobTable.createdAt].toEpochMilli(),
                        workType = job[JobTable.workType],
                        jobLocation = job[JobTable.jobLocation],
                        jobType = job[JobTable.jobType],
                        jobDescription = job[JobTable.jobDescription],
                        jobSalary = JobSalary(
                            minSalary = job[JobTable.minSalary],
                            maxSalary = job[JobTable.maxSalary],
                        ),
                        education = job[JobTable.education],
                        experience = job[JobTable.experience],
                    )
                }
        }
    }

    suspend fun getAllJobs(): List<Job> {
        return dbQuery {
            (JobTable innerJoin JobTitleTable)
                .slice(JobTable.columns + JobTitleTable.columns)
                .selectAll()
                .map { mapJobFromResultRow(it) }
        }
    }

    suspend fun getPopularJobs(limit: Int): List<Job> {
        return dbQuery {
            (JobTable innerJoin JobTitleTable)
                .slice(JobTable.columns + JobTitleTable.columns)
                .select { JobTitleTable.title.isNotNull() }
                .take(limit)
                .map { mapJobFromResultRow(it) }
        }
    }

    //endregion


    private fun mapJobFromResultRow(row: ResultRow): Job {
        return Job(
            id = row[JobTable.id].value.toString(),
            jobTitle = JobTitle(
                id = row[JobTitleTable.id].value,
                title = row[JobTitleTable.title]
            ),
            creatorId = row[JobTable.creatorId].value.toString(),
            company = row[JobTable.company],
            createdAt = row[JobTable.createdAt].toEpochMilli(),
            workType = row[JobTable.workType],
            jobLocation = row[JobTable.jobLocation],
            jobType = row[JobTable.jobType],
            jobDescription = row[JobTable.jobDescription],
            jobSalary = JobSalary(
                minSalary = row[JobTable.minSalary],
                maxSalary = row[JobTable.maxSalary]
            ),
            education = row[JobTable.education],
            experience = row[JobTable.experience]
        )
    }

}