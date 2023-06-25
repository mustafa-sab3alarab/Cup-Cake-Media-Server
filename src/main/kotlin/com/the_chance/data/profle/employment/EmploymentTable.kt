package com.the_chance.data.profle.employment

import com.the_chance.utils.*
import org.jetbrains.exposed.dao.id.UUIDTable

object EmploymentTable : UUIDTable() {
    val position = text(POSITION)
    val company = text(COMPANY)
    val startDate = text(START_DATE)
    val endDate = text(END_DATE)
}