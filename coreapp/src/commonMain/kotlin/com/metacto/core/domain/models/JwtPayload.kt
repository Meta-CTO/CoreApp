package com.metacto.core.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class JwtPayload(
    val exp: Long
)