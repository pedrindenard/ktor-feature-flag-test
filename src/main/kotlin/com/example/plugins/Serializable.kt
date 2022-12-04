package com.example.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

object Serializable {

    fun Application.configureSerializable() {
        install(ContentNegotiation) {
            json()
        }
    }
}