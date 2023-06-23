package com.the_chance.data.job

import com.the_chance.data.jobTitle.JobTitleTable
import com.the_chance.data.user.UserTable
import com.the_chance.utils.*
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object JobTable : UUIDTable() {
    val jobTitleId = reference(JOB_TITLE_ID, JobTitleTable.id)
    val creatorId = reference(USER_ID, UserTable.id)
    val company = text(COMPANY)
    val createdAt = datetime(CREATE_AT).defaultExpression(CurrentDateTime)
    val workType = text(WORK_TYPE)
    val jobLocation = text(JOB_LOCATION)
    val jobType = text(JOB_TYPE)
    val jobDescription = text(JOB_DESCRIPTION)
    val minSalary = double(MIN_SALARY)
    val maxSalary = double(MAX_SALARY)
    val experience = text(EXPERIENCE)
    val education = text(EDUCATION)
    val skills = text(SKILLS)
}