package com.the_chance.utils

fun Double?.orZero() = this ?: 0.0

fun String.isInt() = this.toIntOrNull() != null