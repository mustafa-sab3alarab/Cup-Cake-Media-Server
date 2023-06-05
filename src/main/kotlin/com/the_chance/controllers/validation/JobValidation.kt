package com.the_chance.controllers.validation

import com.the_chance.data.job.Job

fun isJobFieldsNotEmpty(job: Job): Boolean {
    with(job) {
        return (company.isEmpty() || workType.isEmpty() || jobLocation.isEmpty() || jobType.isEmpty() || jobDescription.isEmpty()).not()
    }
}