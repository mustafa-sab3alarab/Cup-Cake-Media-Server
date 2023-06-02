package com.the_chance.controllers.validation

fun isValidContent(content: String?): Boolean = content.isNullOrEmpty().not()