package com.the_chance.endpoints

import com.the_chance.controllers.JobController
import com.the_chance.data.job.Job
import com.the_chance.data.job.JobSalary
import com.the_chance.data.jobTitle.JobTitle
import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.utils.tryQuery
import com.the_chance.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.jobRoutes(jobController: JobController) {

    authenticate("auth-jwt") {

        route("/user") {

            route("/job") {

                post {
                    tryQuery {
                        val principal = call.principal<JWTPrincipal>()
                        val userId = principal?.subject

                        val params = call.receiveParameters()
                        val jobTitleId = params[JOB_TITLE_ID]?.trim()?.toIntOrNull() ?: -1
                        val company = params[COMPANY]?.trim().orEmpty()
                        val workType = params[WORK_TYPE]?.trim().orEmpty()
                        val jobLocation = params[JOB_LOCATION]?.trim().orEmpty()
                        val jobType = params[JOB_TYPE]?.trim().orEmpty()
                        val jobDescription = params[JOB_DESCRIPTION]?.trim().orEmpty()
                        val minSalary = params[MIN_SALARY]?.trim()?.toDoubleOrNull() ?: -1.0
                        val maxSalary = params[MAX_SALARY]?.trim()?.toDoubleOrNull() ?: -1.0
                        val experience = params[EXPERIENCE]?.trim().orEmpty()
                        val education = params[EDUCATION]?.trim().orEmpty()
                        val skills = params[SKILLS]?.trim().orEmpty()

                        jobController.createJob(
                            Job(
                                jobTitle = JobTitle(jobTitleId),
                                creatorId = userId ?: "",
                                company = company,
                                workType = workType,
                                jobLocation = jobLocation,
                                jobType = jobType,
                                jobDescription = jobDescription,
                                jobSalary = JobSalary(minSalary, maxSalary),
                                experience = experience,
                                education = education,
                                skills = skills
                            )
                        )

                        call.respond(
                            HttpStatusCode.Created,
                            ServerResponse.success(Unit, "Job created successfully")
                        )
                    }
                }

                delete("/{$ID}") {
                    tryQuery {
                        val principal = call.principal<JWTPrincipal>()
                        val userId = principal?.subject

                        val jobId = call.parameters[ID]?.trim()
                        jobController.deleteJob(userId, jobId)
                        call.respond(HttpStatusCode.Accepted, ServerResponse.success("Job deleted successfully"))
                    }
                }

            }

            route("/jobs") {

                get {
                    tryQuery {
                        val principal = call.principal<JWTPrincipal>()
                        val userId = principal?.subject

                        val jobs = jobController.getAllUserJobs(userId)
                        call.respond(HttpStatusCode.OK, ServerResponse.success(jobs))
                    }
                }


            }

        }

        route("/public") {

            route("/job") {

                get("/{$ID}") {
                    tryQuery {
                        val jobId = call.parameters[ID]?.trim()
                        val job = jobController.getJobById(jobId)
                        call.respond(HttpStatusCode.OK, ServerResponse.success(job))
                    }
                }

            }

            route("/jobs") {

                get {
                    tryQuery {
                        val jobs = jobController.getAllJobs()
                        call.respond(HttpStatusCode.OK, ServerResponse.success(jobs))
                    }
                }

                get("/popular") {
                    tryQuery {
                        val limit = call.parameters["limit"]?.toIntOrNull() ?: 10
                        val popularJobs = jobController.getPopularJobs(limit)
                        call.respond(HttpStatusCode.OK, ServerResponse.success(popularJobs))
                    }
                }

            }

        }
    }
}