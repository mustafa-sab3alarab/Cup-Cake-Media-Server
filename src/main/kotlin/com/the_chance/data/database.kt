package com.the_chance.data

import org.jetbrains.exposed.sql.Database

fun getDataBase(): Database {

    val host = System.getenv("host")
    val port = System.getenv("port")
    val databaseName = System.getenv("databaseName")
    val databaseUsername = System.getenv("databaseUsername")
    val databasePassword = System.getenv("databasePassword")

    return Database.connect(
        "jdbc:postgresql://$host:$port/$databaseName",
        driver = "org.postgresql.Driver",
        user = databaseUsername,
        password = databasePassword
    )
}