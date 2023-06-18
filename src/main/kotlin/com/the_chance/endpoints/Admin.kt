package com.the_chance.endpoints

import com.the_chance.data.AppDatabase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Routing.adminRoute(database: AppDatabase) {
    authenticate("auth-jwt") {
        route("/admin/controller") {
            delete("dropTables") {
                database.dropTables()
                call.respondText { "Done ):" }
            }
        }
    }
}