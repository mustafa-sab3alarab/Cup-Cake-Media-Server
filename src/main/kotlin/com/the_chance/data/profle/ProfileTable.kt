package com.the_chance.data.profle

import com.the_chance.data.jobTitle.JobTitleTable
import com.the_chance.utils.*
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption

object ProfileTable : UUIDTable() {
    var bio = text(BIO).default(EMPTY)
    val avatar = varchar(AVATAR, 250).default(DEFAULT_AVATAR_USER).default(DEFAULT_AVATAR_USER)
    val location = varchar(LOCATION, 250).default(DEFAULT_AVATAR_USER).default(EMPTY)
    val linkWebsite = varchar(LINK_WEBSITE, 250).default(DEFAULT_AVATAR_USER).default(EMPTY)
    val jobTitleId = integer(JOB_TITLE_ID).references(JobTitleTable.id, onDelete = ReferenceOption.CASCADE)

}