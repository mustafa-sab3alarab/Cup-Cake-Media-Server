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
            it[this.skills] = job.skills
        }
    }

    suspend fun getAllUserJobs(userId: UUID): List<Job> {
        return dbQuery {
            (JobTable innerJoin JobTitleTable)
                .slice(JobTable.columns + JobTitleTable.columns)
                .select { JobTable.creatorId eq userId }
                .orderBy(JobTable.createdAt to SortOrder.DESC)
                .map { it.toJob() }
        }
    }


    suspend fun deleteJob(jobId: UUID) {
        return dbQuery {
            val deleteResult = JobTable.deleteWhere { (JobTable.id eq jobId) }
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
                        createdAt = job[JobTable.createdAt].toString(),
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
                        skills = job[JobTable.skills]
                    )
                }
        }
    }

    suspend fun getAllJobs(): List<Job> {
        return dbQuery {
            (JobTable innerJoin JobTitleTable)
                .slice(JobTable.columns + JobTitleTable.columns)
                .selectAll()
                .orderBy(JobTable.createdAt to SortOrder.DESC)
                .map { it.toJob() }
        }
    }

    suspend fun getPopularJobs(limit: Int): List<Job> {
        return dbQuery {
            (JobTable innerJoin JobTitleTable)
                .slice(JobTable.columns + JobTitleTable.columns)
                .select { JobTitleTable.title.isNotNull() }
                .take(limit)
                .map { it.toJob() }
        }
    }

    //endregion


    private fun ResultRow.toJob(): Job {
        return Job(
            id = this[JobTable.id].value.toString(),
            jobTitle = JobTitle(
                id = this[JobTitleTable.id].value,
                title = this[JobTitleTable.title]
            ),
            creatorId = this[JobTable.creatorId].value.toString(),
            company = this[JobTable.company],
            createdAt = this[JobTable.createdAt].toString(),
            workType = this[JobTable.workType],
            jobLocation = this[JobTable.jobLocation],
            jobType = this[JobTable.jobType],
            jobDescription = this[JobTable.jobDescription],
            jobSalary = JobSalary(
                minSalary = this[JobTable.minSalary],
                maxSalary = this[JobTable.maxSalary]
            ),
            education = this[JobTable.education],
            experience = this[JobTable.experience],
            skills = this[JobTable.skills]
        )
    }

}