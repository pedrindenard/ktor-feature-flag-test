package com.example.plugins

import com.example.dao.FeatureTable
import com.example.model.BaseResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

object Routing {

    fun Application.configureRouting() = routing {
        get(path = "/featureFlag") {
            Database.dbQuery {
                val result = FeatureTable.selectAll().map(::resultRowToNotes)
                call.respond(HttpStatusCode.OK, result.first())
            }
        }

        post(path = "/edit") {
            val body = call.receive<BaseResponse>()

            Database.dbQuery {
                val result = FeatureTable.update({ FeatureTable.id eq 1 }) {
                    it[FeatureTable.sttIsEnable] = body.sttIsEnable
                    it[FeatureTable.attachIsEnable] = body.attachIsEnable
                    it[FeatureTable.cameraIsEnable] = body.cameraIsEnable
                } > 0

                if (result) {
                    call.respondRedirect(url = "/featureFlag")
                } else {
                    call.respond(HttpStatusCode.BadRequest, message = "Something went wrong.")
                }
            }
        }
    }

    @Suppress(names = ["unused"])
    fun Application.configureInsert() {
        CoroutineScope(Dispatchers.IO).launch {
            Database.dbQuery {
                if (FeatureTable.selectAll().map(::resultRowToNotes).isEmpty()) {
                    FeatureTable.insert {
                        it[FeatureTable.id] = 1
                        it[FeatureTable.sttIsEnable] = true
                        it[FeatureTable.attachIsEnable] = true
                        it[FeatureTable.cameraIsEnable] = true
                    }
                }
            }
        }
    }

    private fun resultRowToNotes(row: ResultRow) = BaseResponse(
        sttIsEnable = row[FeatureTable.sttIsEnable],
        attachIsEnable = row[FeatureTable.attachIsEnable],
        cameraIsEnable = row[FeatureTable.cameraIsEnable]
    )
}