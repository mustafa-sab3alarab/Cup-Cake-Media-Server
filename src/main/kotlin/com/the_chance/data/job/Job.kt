package com.the_chance.data.job

import kotlinx.serialization.Serializable

@Serializable
data class Job(
    val id : String = "",
    val jobTitleId: Int,
    val company: String,
    val createdAt: Long = 0L,
    val workType: String,
    val jobLocation: String,
    val jobType: String,
    val jobDescription: String,
    val jobSalary: Double
)