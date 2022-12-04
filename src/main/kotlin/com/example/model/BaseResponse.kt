package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse(
    val sttIsEnable: Boolean,
    val attachIsEnable: Boolean,
    val cameraIsEnable: Boolean
)