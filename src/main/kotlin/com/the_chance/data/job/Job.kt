package com.the_chance.data.job

import com.the_chance.data.jobTitle.JobTitle
import kotlinx.serialization.Serializable

@Serializable
data class Job(
    val id: String = "",
    val creatorId: String,
    val jobTitle: JobTitle,
    val company: String,
    val createdAt: String = "",
    val workType: String,
    val jobLocation: String,
    val jobType: String,
    val jobDescription: String,
    val jobSalary: JobSalary,
    val experience: String,
    val education: String,
    val skills : String
)

@Serializable
data class JobSalary(
    val minSalary: Double,
    val maxSalary: Double,
)