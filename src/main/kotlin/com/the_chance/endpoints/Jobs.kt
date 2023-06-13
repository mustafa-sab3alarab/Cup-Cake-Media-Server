package com.the_chance.endpoints

import com.the_chance.controllers.JobController
import com.the_chance.data.job.Job
import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.utils.tryQuery
import com.the_chance.utils.ID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.jobRoutes(jobController: JobController) {

    authenticate("auth-jwt") {

        post("/job") {
            tryQuery {
                val params = call.receiveParameters()
                val jobTitleId = params["jobTitleId"]?.trim()?.toIntOrNull()
                val company = params["company"]?.trim().orEmpty()
                val workType = params["workType"]?.trim().orEmpty()
                val jobLocation = params["jobLocation"]?.trim().orEmpty()
                val jobType = params["jobType"]?.trim().orEmpty()
                val jobDescription = params["jobDescription"]?.trim().orEmpty()
                val jobSalary = params["jobSalary"]?.trim()?.toDoubleOrNull()

                val newJob = jobController.createJob(
                    Job(
                        jobTitleId = jobTitleId ?: -1,
                        company = company,
                        workType = workType,
                        jobLocation = jobLocation,
                        jobType = jobType,
                        jobDescription = jobDescription,
                        jobSalary = jobSalary ?: -1.0
                    )
                )

                call.respond(
                    HttpStatusCode.Created,
                    ServerResponse.success(newJob, successMessage = "Job created successfully")
                )
            }
        }

        delete("/job/{$ID}") {
            tryQuery {
                val jobId = call.parameters[ID]?.trim()
                jobController.deleteJob(jobId)
                call.respond(HttpStatusCode.Accepted, ServerResponse.success("Job deleted successfully"))
            }
        }


        get("/jobs") {
            tryQuery {
                val jobs = jobController.getAllJobs()
                call.respond(HttpStatusCode.OK, ServerResponse.success(jobs))
            }
        }

        get("/job/{id}") {
            tryQuery {
                val jobId = call.parameters[ID]?.trim()
                val job = jobController.getJobById(jobId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(job))
            }
        }
    }
}