package com.the_chance.utils

fun validationTitle(title: String): String? {
    return title.trim().takeIf { it.length !in 4..250 }?.let {
        "Title should be outside the range of 4 to 250 characters"
    } ?: title.takeUnless { isAlphabetWithSpace(it) }?.let {
        "Title should contain only alphabets and spaces"
    }
}

private fun isAlphabetWithSpace(input: String): Boolean {
    val regex = Regex("^[a-zA-Z ]+\$")
    return input.matches(regex)
}