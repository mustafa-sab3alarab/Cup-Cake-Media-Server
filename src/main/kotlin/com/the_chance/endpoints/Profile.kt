package com.the_chance.endpoints

import com.the_chance.controllers.ProfileController
import com.the_chance.data.profle.education.Education
import com.the_chance.data.profle.education.EducationDate
import com.the_chance.data.utils.ServerResponse
import com.the_chance.endpoints.utils.tryQuery
import com.the_chance.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.profileRoutes(profileController: ProfileController) {
    authenticate("auth-jwt") {

        route("/profile") {

            post("/education") {
                tryQuery {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.subject

                    val params = call.receiveParameters()
                    val degree = params[DEGREE]?.trim().orEmpty()
                    val school = params[SCHOOL]?.trim().orEmpty()
                    val city = params[CITY]?.trim().orEmpty()
                    val startDate = params[START_DATE]?.trim().orEmpty()
                    val endDate = params[END_DATE]?.trim().orEmpty()

                    val education = Education(
                        degree = degree,
                        school = school,
                        city = city,
                        date = EducationDate(startDate, endDate)
                    )
                    profileController.insertEducation(userId, education)
                    call.respond(HttpStatusCode.Created, ServerResponse.success(Unit, "Education created successfully"))
                }
            }

            get("/educations") {
                tryQuery {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.subject

                    val educations = profileController.getAllUserEducation(userId)
                    call.respond(HttpStatusCode.OK, ServerResponse.success(educations))
                }
            }

            delete("/education/{$EDUCATION_ID}") {
                tryQuery {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.subject

                    val educationId = call.parameters[EDUCATION_ID]
                    profileController.deleteEducation(userId, educationId)
                    call.respond(
                        HttpStatusCode.Accepted,
                        ServerResponse.success(Unit, "Education deleted successfully")
                    )
                }
            }

            put("/education/{$EDUCATION_ID}") {
                tryQuery {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.subject

                    val educationId = call.parameters[EDUCATION_ID]
                    val params = call.receiveParameters()
                    val degree = params[DEGREE]?.trim().orEmpty()
                    val school = params[SCHOOL]?.trim().orEmpty()
                    val city = params[CITY]?.trim().orEmpty()
                    val startDate = params[START_DATE]?.trim().orEmpty()
                    val endDate = params[END_DATE]?.trim().orEmpty()

                    val education = Education(
                        degree = degree,
                        school = school,
                        city = city,
                        date = EducationDate(startDate, endDate)
                    )

                    val newEducation = profileController.updateEducation(userId, educationId, education)
                    call.respond(
                        HttpStatusCode.Accepted,
                        ServerResponse.success(newEducation, "Education updated successfully")
                    )
                }

            }

            post("/skill") {
                tryQuery {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.subject

                    val params = call.receiveParameters()
                    val skill = params[SKILL]?.trim().orEmpty()

                    profileController.insertSkill(userId, skill)
                    call.respond(HttpStatusCode.Created, ServerResponse.success(Unit, "Skill created successfully"))
                }
            }

            get("/skills") {
                tryQuery {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.subject

                    val skills = profileController.getAllUserSkills(userId)
                    call.respond(HttpStatusCode.OK, ServerResponse.success(skills))
                }
            }

            delete("/skill/{$SKILL_ID}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject

                val skillId = call.parameters[SKILL_ID]
                profileController.deleteSkill(userId, skillId)
                call.respond(
                    HttpStatusCode.Accepted,
                    ServerResponse.success(Unit, "Skill deleted successfully")
                )
            }

            put("/skill/{$SKILL_ID}") {
                tryQuery {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.subject

                    val skillId = call.parameters[SKILL_ID]
                    val params = call.receiveParameters()
                    val skill = params[SKILL]?.trim().orEmpty()

                    val newSkill = profileController.updateSkill(userId, skillId, skill)
                    call.respond(
                        HttpStatusCode.Accepted,
                        ServerResponse.success(newSkill, "Skill updated successfully")
                    )
                }

            }
        }
    }
}