package com.the_chance.data.profle.skills

import com.the_chance.data.profle.education.Education
import com.the_chance.data.profle.education.EducationDate
import com.the_chance.data.profle.education.EducationTable
import com.the_chance.data.utils.dbQuery
import com.the_chance.utils.DeleteError
import com.the_chance.utils.UpdateEducationError
import com.the_chance.utils.UpdateSkillError
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class SkillService {
    suspend fun createSkill(userUUID: UUID, skill: String) {
        dbQuery {
            SkillsTable.insert {
                it[this.skill] = skill
                it[this.userId] = userUUID
            }
        }
    }

    suspend fun getAllUserSkills(userUUID: UUID): List<Skill> {
        return dbQuery {
            SkillsTable
                .select { SkillsTable.userId eq userUUID }
                .orderBy(SkillsTable.createdAt to SortOrder.DESC)
                .map { it.toSkill() }
        }
    }


    suspend fun getSkillById(skillUUID: UUID): Skill? {
        return dbQuery {
            SkillsTable.select {
                SkillsTable.id eq skillUUID
            }.singleOrNull()?.let { skill ->
                Skill(
                    id = skill[SkillsTable.id].value.toString(),
                    userId = skill[SkillsTable.userId].value.toString(),
                    skill = skill[SkillsTable.skill]
                )
            }
        }
    }

    suspend fun deleteSkill(skillUUID: UUID) {
        return dbQuery {
            val deleteResult = SkillsTable.deleteWhere { (SkillsTable.id eq skillUUID) }
            if (deleteResult != 1) {
                throw DeleteError()
            }
        }
    }


    suspend fun updateSkill(skillUUID: UUID, skill: String): Skill = dbQuery {
        SkillsTable.update({ SkillsTable.id eq skillUUID }) {
            it[this.skill] = skill
        }

        SkillsTable
            .select { SkillsTable.id eq skillUUID }
            .singleOrNull()?.toSkill() ?: throw UpdateSkillError
    }


    private fun ResultRow.toSkill(): Skill {
        return Skill(
            id = this[SkillsTable.id].value.toString(),
            userId = this[SkillsTable.userId].value.toString(),
            skill = this[SkillsTable.skill]
        )
    }

}