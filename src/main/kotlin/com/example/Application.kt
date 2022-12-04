package com.example

import com.example.plugins.Database.configureDatabase
import com.example.plugins.Routing.configureInsert
import com.example.plugins.Routing.configureRouting
import com.example.plugins.Serializable.configureSerializable
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    configureDatabase()
    configureSerializable()
    configureRouting()
    configureInsert()
}
