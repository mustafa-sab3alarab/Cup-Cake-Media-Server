package com.the_chance

import com.the_chance.data.products.ProductService
import com.the_chance.data.getDataBase
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.the_chance.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    val database = getDataBase()

    val productService = ProductService(database)
    configureSerialization()
    configureMonitoring()
    configureRouting(productService)
}
