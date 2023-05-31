package com.the_chance.data.jobTitle

import kotlinx.serialization.Serializable

@Serializable
data class JobTitle(
    val id: Int,
    val title: String,
)