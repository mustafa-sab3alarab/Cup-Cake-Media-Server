package com.the_chance.data

import com.the_chance.data.comment.CommentTable
import com.the_chance.data.image.ImageTable
import com.the_chance.data.job.JobTable
import com.the_chance.data.jobTitle.JobTitleTable
import com.the_chance.data.post.PostTable
import com.the_chance.data.profle.ProfileTable
import com.the_chance.data.profle.education.EducationTable
import com.the_chance.data.profle.employment.EmploymentTable
import com.the_chance.data.profle.skills.SkillsTable
import com.the_chance.data.user.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object AppDatabase {

    private val tables by lazy {
        arrayOf(
            ImageTable,
            JobTable,
            JobTitleTable,
            PostTable,
            UserTable,
            ProfileTable,
            CommentTable,
            EducationTable,
            SkillsTable,
            EmploymentTable
        )
    }

    init {
        createTables()
    }

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


    private fun createTables() {
        transaction(getDataBase()) {
            tables.forEach {
                SchemaUtils.create(it)
            }
        }
    }

    fun dropTables() {
        transaction(getDataBase()) {
            SchemaUtils.drop(*tables)
        }
        createTables()
    }
}