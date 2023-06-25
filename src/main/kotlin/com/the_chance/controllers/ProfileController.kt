package com.the_chance.controllers

import com.the_chance.controllers.validation.isEducationFieldsNotEmpty
import com.the_chance.controllers.validation.isValidUUID
import com.the_chance.data.profle.education.Education
import com.the_chance.data.profle.education.EducationService
import com.the_chance.data.profle.skills.Skill
import com.the_chance.data.profle.skills.SkillService
import com.the_chance.utils.EducationFieldsError
import com.the_chance.utils.SkillFieldsError
import com.the_chance.utils.Unauthorized

class ProfileController(
    private val educationService: EducationService,
    private val skillService: SkillService
) {

    suspend fun insertEducation(userId: String?, education: Education) {
        val userUUID = isValidUUID(userId)
        checkIfEducationFieldsNotEmpty(education)
        educationService.createEducation(userUUID, education)
    }

    suspend fun getAllUserEducation(userId: String?): List<Education> {
        val userUUID = isValidUUID(userId)
        return educationService.getAllUserEducation(userUUID)
    }

    suspend fun deleteEducation(userId: String?, educationId: String?) {
        val educationUUID = isValidUUID(educationId)
        educationService.getEducationById(educationUUID)?.takeIf {
            it.userId == userId
        }?.let {
            educationService.deleteEducation(educationUUID)
        } ?: throw Unauthorized
    }

    suspend fun insertSkill(userId: String?, skill: String) {
        skill.takeIf { it.isNotEmpty() }?.let {
            val userUUID = isValidUUID(userId)
            skillService.createSkill(userUUID,skill)
        } ?: throw SkillFieldsError
    }

    suspend fun getAllUserSkills(userId: String?): List<Skill> {
        val userUUID = isValidUUID(userId)
        return skillService.getAllUserSkills(userUUID)
    }

    suspend fun deleteSkill(userId: String?, skillId: String?) {
        val skillUUID = isValidUUID(skillId)
        skillService.getSkillById(skillUUID)?.takeIf {
            it.userId == userId
        }?.let {
            skillService.deleteSkill(skillUUID)
        } ?: throw Unauthorized
    }

    private fun checkIfEducationFieldsNotEmpty(education: Education) {
        if (!isEducationFieldsNotEmpty(education)) throw EducationFieldsError
    }


}