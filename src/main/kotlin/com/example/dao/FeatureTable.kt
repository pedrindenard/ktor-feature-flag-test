package com.example.dao

import org.jetbrains.exposed.sql.Table

object FeatureTable : Table() {
    val id = integer(name = "id")
    val sttIsEnable = bool(name = "stt_is_enable")
    val attachIsEnable = bool(name = "attach_is_enable")
    val cameraIsEnable = bool(name = "camera_is_enable")

    override val primaryKey = PrimaryKey(id)
}