package com.the_chance.endpoints

import com.the_chance.data.job.Job
import com.the_chance.data.job.JobService
import com.the_chance.data.jobTitle.JobTitleService
import com.the_chance.data.utils.ServerResponse
import com.the_chance.utils.isValidUUID
import com.the_chance.utils.salaryValidation
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.jobRoutes(jobService: JobService, jobTitleService: JobTitleService) {

    post("/job") {

        try {
            val params = call.receiveParameters()

            val jobTitleId = params["jobTitleId"]?.trim().orEmpty()
            val company = params["company"]?.trim().orEmpty()
            val workType = params["workType"]?.trim().orEmpty()
            val jobLocation = params["jobLocation"]?.trim().orEmpty()
            val jobType = params["jobType"]?.trim().orEmpty()
            val jobDescription = params["jobDescription"]?.trim().orEmpty()
            val jobSalary = params["jobSalary"]?.trim().orEmpty()

            if (jobTitleId.toIntOrNull() == null) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid job title ID."))
            } else {
                if (!jobTitleService.isJobTitleIdValid(jobTitleId.toInt())) {
                    call.respond(HttpStatusCode.NotFound, ServerResponse.error("Job title ID does not exist"))
                }
            }

            if (company.isEmpty() || workType.isEmpty() || jobLocation.isEmpty() || jobType.isEmpty() || jobDescription.isEmpty()) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("All fields are required.."))
            }

            if (!salaryValidation(jobSalary)) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid salary."))
            }

            val newJob = jobService.createJob(
                Job(
                    jobTitleId = jobTitleId.toInt(),
                    company = company,
                    workType = workType,
                    jobLocation = jobLocation,
                    jobType = jobType,
                    jobDescription = jobDescription,
                    jobSalary = jobSalary.toDouble()
                )
            )
            call.respond(
                HttpStatusCode.Created,
                ServerResponse.success(newJob, successMessage = "Job created successfully")
            )
        } catch (e: Exception) {
            call.respond(ServerResponse.error(e.message.toString()))
        }
    }

    get("/job") {
        jobService.getAllJobs().takeIf { it.isNotEmpty() }?.let {
            call.respond(HttpStatusCode.OK, ServerResponse.success(it))
        } ?: call.respond(HttpStatusCode.NoContent)
    }

    get("/job/{id}") {

        suspend fun fetchJob(jobId: String) {
            jobService.getJobById(jobId)?.let { job ->
                call.respond(HttpStatusCode.OK, ServerResponse.success(job))
            } ?: call.respond(HttpStatusCode.NotFound, ServerResponse.error("Opps!, this job not found."))
        }

        call.parameters["id"]?.let { id ->
            id.takeIf {
                isValidUUID(it)
            }?.let { jobId ->
                fetchJob(jobId)
            } ?: call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid job ID."))
        }
    }

    delete("/job/{id}") {
        val jobId = call.parameters["id"]?.let { id ->
            id.takeIf { isValidUUID(it) }
        }

        jobId?.let {
            if (jobService.isJobAvailable(it)) {
                jobService.deleteJob(it)
                call.respond(HttpStatusCode.Accepted, ServerResponse.success("Job deleted successfully"))
            } else {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.success("Job not exist"))
            }
        } ?: call.respond(HttpStatusCode.BadRequest, ServerResponse.success("Invalid job ID."))

    }
}
