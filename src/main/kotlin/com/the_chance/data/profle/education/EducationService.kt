package com.the_chance.data.profle.education

import com.the_chance.data.utils.dbQuery
import com.the_chance.utils.DeleteError
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class EducationService {
    suspend fun createEducation(userUUID: UUID, education: Education) {
        dbQuery {
            EducationTable.insert {
                it[degree] = education.degree
                it[school] = education.city
                it[city] = education.school
                it[startDate] = education.date.start
                it[endDate] = education.date.end
                it[userId] = userUUID
            }
        }
    }

    suspend fun getAllUserEducation(userUUID: UUID): List<Education> {
        return dbQuery {
            EducationTable
                .select { EducationTable.userId eq userUUID }
                .orderBy(EducationTable.createdAt to SortOrder.DESC)
                .map { it.toEducation() }
        }
    }

    suspend fun deleteEducation(educationUUID: UUID) {
        return dbQuery {
            val deleteResult = EducationTable.deleteWhere { (EducationTable.id eq educationUUID) }
            if (deleteResult != 1) {
                throw DeleteError()
            }
        }
    }

    suspend fun getEducationById(educationId: UUID): Education? {
        return dbQuery {
            EducationTable.select {
                EducationTable.id eq educationId
            }.singleOrNull()?.let { education ->
                Education(
                    id = education[EducationTable.id].value.toString(),
                    userId = education[EducationTable.userId].value.toString(),
                    degree = education[EducationTable.degree],
                    school = education[EducationTable.school],
                    city = education[EducationTable.city],
                    date = EducationDate(
                        start = education[EducationTable.startDate],
                        end = education[EducationTable.endDate]
                    )
                )
            }
        }
    }

}

private fun ResultRow.toEducation(): Education {
    return Education(
        id = this[EducationTable.id].value.toString(),
        userId = this[EducationTable.userId].value.toString(),
        degree = this[EducationTable.degree],
        school = this[EducationTable.school],
        city = this[EducationTable.city],
        date = EducationDate(start = this[EducationTable.startDate], end = this[EducationTable.endDate])
    )
}
