package com.the_chance.data.job

import com.the_chance.data.jobTitle.JobTitleTable
import org.jetbrains.exposed.dao.id.UUIDTable
import java.time.Instant

object JobTable : UUIDTable() {
    val jobTitleId = reference("jobTitleId", JobTitleTable.id)
    val company = text("company")
    val createdAt = long("createdAt").default(Instant.now().toEpochMilli())
    val workType = text("workType")
    val jobLocation = text("jobLocation")
    val jobType = text("jobType")
    val jobDescription = text("jobDescription")
    val salary = double("salary")
}