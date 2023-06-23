package com.the_chance.controllers.validation

import com.the_chance.utils.InValidJobTitleIdError

fun isJobTitleValid(jobTitle: Int) {
    if (jobTitle == -1) throw InValidJobTitleIdError
}