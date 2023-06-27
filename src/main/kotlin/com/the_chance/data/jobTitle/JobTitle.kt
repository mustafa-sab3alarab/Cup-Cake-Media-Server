package com.the_chance.data.jobTitle

import kotlinx.serialization.Serializable

@Serializable
data class JobTitle(
    val id: Int,
    val title: String? = "",
)
@Serializable
data class JobTitles(val results: List<JsonJobTitle>)

@Serializable
data class JsonJobTitle(
    val objectId: String? = "",
    val title: String = "",
    val createdAt: String? = "",
    val updatedAt: String? = ""
)
