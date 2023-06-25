package com.the_chance.data.profle.skills

import kotlinx.serialization.Serializable

@Serializable
data class Skill(
    val id : String = "",
    val userId : String = "",
    val skill: String
)