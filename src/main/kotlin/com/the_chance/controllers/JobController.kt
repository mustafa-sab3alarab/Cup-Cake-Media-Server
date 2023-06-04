package com.the_chance.controllers

import com.the_chance.controllers.validation.isJobFieldsNotEmpty
import com.the_chance.controllers.validation.isJobTitleValid
import com.the_chance.controllers.validation.isSalaryValid
import com.the_chance.controllers.validation.isValidUUID
import com.the_chance.data.job.Job
import com.the_chance.data.job.JobService
import com.the_chance.data.job.JobWithJobTitle
import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.utils.*

class JobController(private val jobService: JobService,private val jobTitleService: JobTitleService) {

    suspend fun createJob(job: Job): Job {
        checkJobTitleValidation(job.jobTitleId)
        isJobTitleExist(job)
        checkJobFieldsValidation(job)
        checkSalaryValidation(job.jobSalary)

        return jobService.createJob(job)
    }

    suspend fun getAllJobs(): List<JobWithJobTitle> {
        return jobService.getAllJobs()
    }

    suspend fun getJobById(jobId: String?): Job {
        val jobUUID = isValidUUID(jobId)
        return jobService.getJobById(jobUUID) ?: throw NoJobFoundError()

    }

    suspend fun deleteJob(jobId: String?) {
        val jobUUID = isValidUUID(jobId)
        jobService.deleteJob(jobUUID)
    }

    private fun checkJobTitleValidation(jobTitleId: Int): Boolean {
        return if (isJobTitleValid(jobTitleId)) true else throw InValidJobTitleIdError()
    }

    private suspend fun isJobTitleExist(job: Job): Boolean {
        return if (jobTitleService.checkIfJobTitleExist(job.jobTitleId)) true else throw NoJobTitleFoundError()
    }

    private fun checkJobFieldsValidation(job: Job): Boolean {
        return if (isJobFieldsNotEmpty(job)) true else throw InValidJobError()

    }

    private fun checkSalaryValidation(salary: Double): Boolean {
        return if (isSalaryValid(salary)) true else throw InValidSalaryError()
    }
}
