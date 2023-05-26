package com.the_chance.data.products

import org.jetbrains.exposed.dao.id.UUIDTable

object ProductsTable : UUIDTable() {
    val name = text("name")
    val price = double("price")
}