package com.the_chance.data.profle.education

import com.the_chance.data.user.UserTable
import com.the_chance.utils.*
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object EducationTable : UUIDTable() {
    val degree = text(DEGREE)
    val school = text(SCHOOL)
    val city = text(CITY)
    val startDate = text(START_DATE)
    val endDate = text(END_DATE)
    val createdAt = datetime(CREATE_AT).defaultExpression(CurrentDateTime)
    val userId = reference(USER_ID, UserTable.id)
}