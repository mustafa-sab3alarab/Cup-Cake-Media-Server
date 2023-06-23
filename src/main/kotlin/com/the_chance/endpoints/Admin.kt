package com.the_chance.endpoints

import com.the_chance.controllers.AdminController
import com.the_chance.data.utils.ServerResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Routing.adminRoute(adminController: AdminController) {
    route("/admin/controller") {
        delete("dropTables") {
            adminController.dropTables()
            call.respond(HttpStatusCode.OK, ServerResponse.success(Unit, successMessage = "Done"))
        }
    }
}