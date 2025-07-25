package com.metacto.core.date

import com.metacto.kmm.core.CommonSerializable
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable(with = CoreDateTimeSerializer::class)
expect class CoreDateTime(
    value: LocalDateTime
) : CommonSerializable {
    val value: LocalDateTime
}