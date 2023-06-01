package com.the_chance.utils


fun salaryValidation(salary: String): Boolean {
    return salary.toDoubleOrNull() != null && salary.toDouble() > 0.0
}