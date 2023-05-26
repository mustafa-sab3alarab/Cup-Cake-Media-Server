package com.the_chance.data.products

import com.the_chance.data.utils.dbQuery
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*

class ProductService(database: Database) {

    init {
        transaction(database) {
            SchemaUtils.create(ProductsTable)
        }
    }

    suspend fun create(productName: String, productPrice: Double): Product = dbQuery {
        val newProduct = ProductsTable.insert {
            it[name] = productName
            it[price] = productPrice
        }
        Product(
            newProduct[ProductsTable.id].value.toString(),
            newProduct[ProductsTable.name],
            newProduct[ProductsTable.price]
        )
    }

    suspend fun getAllProducts(): List<Product> {
        return dbQuery {
            ProductsTable.selectAll().map {
                Product(
                    id = it[ProductsTable.id].value.toString(),
                    name = it[ProductsTable.name].toString(),
                    price = it[ProductsTable.price]
                )
            }
        }
    }


}