package com.the_chance.data.job

import com.the_chance.data.jobTitle.JobTitle
import kotlinx.serialization.Serializable

@Serializable
data class JobWithJobTitle(
    val id: String,
    val jobTitle: JobTitle,
    val company: String,
    val createdAt: Long,
    val workType: String,
    val jobLocation: String,
    val jobType: String,
    val jobDescription: String,
    val jobSalary: Double
)
