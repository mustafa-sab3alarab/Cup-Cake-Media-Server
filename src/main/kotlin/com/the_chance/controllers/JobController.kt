package com.the_chance.controllers

import com.the_chance.controllers.validation.isJobFieldsNotEmpty
import com.the_chance.controllers.validation.isJobTitleValid
import com.the_chance.controllers.validation.isSalaryValid
import com.the_chance.controllers.validation.isValidUUID
import com.the_chance.data.job.Job
import com.the_chance.data.job.JobSalary
import com.the_chance.data.job.JobService
import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.utils.*

class JobController(private val jobService: JobService, private val jobTitleService: JobTitleService) {

    // region user job
    suspend fun createJob(job: Job) {
        val userUUID = isValidUUID(job.creatorId)
        checkJobTitleValidation(job.jobTitle.id)
        isJobTitleExist(job.jobTitle.id)
        checkJobFieldsValidation(job)
        checkSalaryValidation(job.jobSalary)
        jobService.createJob(userUUID, job)
    }

    suspend fun getAllUserJobs(userId: String?): List<Job> {
        val userUUId = isValidUUID(userId)
        return jobService.getAllUserJobs(userUUId)
    }

    suspend fun getRecommendedJobs(userId: String?, limit: Int): List<Job> {
        val userUUID = isValidUUID(userId)
        return jobService.getRecommendedJobs(userUUID, limit)
    }


    suspend fun getTopSalaryJobsInLocation(userId: String?, limit: Int): List<Job> {
        val userUUID = isValidUUID(userId)
        return jobService.getTopSalaryJobsInLocation(userUUID, limit)
    }

    suspend fun getJobsInLocation(userId: String?, limit: Int): List<Job> {
        val userUUID = isValidUUID(userId)
        return jobService.getJobsInLocation(userUUID, limit)
    }

    suspend fun deleteJob(userId: String?, jobId: String?) {
        val jobUUID = isValidUUID(jobId)
        jobService.getJobById(jobUUID)?.takeIf {
            it.creatorId == userId
        }?.let {
            jobService.deleteJob(jobUUID)
        } ?: throw Unauthorized
    }
    //endregion

    //region public job
    suspend fun getJobById(jobId: String?): Job {
        val jobUUID = isValidUUID(jobId)
        return jobService.getJobById(jobUUID) ?: throw NoJobFoundError()
    }


    suspend fun getAllJobs(): List<Job> {
        return jobService.getAllJobs()
    }


    suspend fun getPopularJobs(limit: Int): List<Job> {
        return jobService.getPopularJobs(limit)
    }

    //endregion

    //region checking
    private fun checkJobTitleValidation(jobTitleId: Int): Boolean {
        return if (isJobTitleValid(jobTitleId)) true else throw InValidJobTitleIdError()
    }

    private suspend fun isJobTitleExist(jobTitleId: Int): Boolean {
        return if (jobTitleService.checkIfJobTitleExist(jobTitleId)) true else throw NoJobTitleFoundError()
    }

    private fun checkJobFieldsValidation(job: Job): Boolean {
        return if (isJobFieldsNotEmpty(job)) true else throw InValidJobError()

    }

    private fun checkSalaryValidation(salary: JobSalary): Boolean {
        return if (isSalaryValid(salary.minSalary) && isSalaryValid(salary.maxSalary)) true else throw InValidSalaryError()
    }
    //endregion
}
