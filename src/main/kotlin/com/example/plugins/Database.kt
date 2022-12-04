package com.example.plugins

import com.example.dao.FeatureTable
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object Database {

    @Suppress(names = ["unused"])
    fun Application.configureDatabase() {
        val database = Database.connect(url = "jdbc:h2:file:./build/db", driver = "org.h2.Driver")

        transaction(database) {
            SchemaUtils.create(FeatureTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}