package com.the_chance.data.profle

import com.the_chance.data.jobTitle.JobTitle
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String,
    val bio: String,
    var avatar: String,
    var linkWebsite: String,
    var location: String,
    var jobTitle: JobTitle,
)