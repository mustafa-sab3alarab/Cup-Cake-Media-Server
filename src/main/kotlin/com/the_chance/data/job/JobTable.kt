package com.the_chance.data.job

import com.the_chance.data.jobTitle.JobTitleTable
import com.the_chance.data.user.UserTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object JobTable : UUIDTable() {
    val jobTitleId = reference("jobTitleId", JobTitleTable.id)
    val creatorId = reference("creatorId", UserTable.id)
    val company = text("company")
    val createdAt = timestamp("createdAt").clientDefault { Instant.now() }
    val workType = text("workType")
    val jobLocation = text("jobLocation")
    val jobType = text("jobType")
    val jobDescription = text("jobDescription")
    val minSalary = double("minSalary")
    val maxSalary = double("maxSalary")
    val experience = text("experience")
    val education = text("education")
}