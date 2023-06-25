package com.the_chance.controllers.validation

import com.the_chance.data.profle.education.Education

fun isEducationFieldsNotEmpty(education: Education): Boolean {
    with(education) {
        return (degree.isEmpty() || school.isEmpty() || city.isEmpty() || date.start.isEmpty() || date.end.isEmpty()).not()
    }
}