package com.the_chance.controllers

import com.the_chance.data.AppDatabase

class AdminController(private val appDatabase: AppDatabase) {
    fun dropTables() {
        appDatabase.dropTables()
    }
}