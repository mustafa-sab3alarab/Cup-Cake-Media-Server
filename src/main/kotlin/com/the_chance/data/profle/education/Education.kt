package com.the_chance.data.profle.education

import kotlinx.serialization.Serializable

@Serializable
data class Education(
    val id: String = "",
    val userId: String = "",
    val degree: String,
    val school: String,
    val city: String,
    val date: EducationDate
)


@Serializable
data class EducationDate(
    val start: String,
    val end: String
)