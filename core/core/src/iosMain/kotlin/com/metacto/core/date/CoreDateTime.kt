package com.metacto.core.date

import com.metacto.kmm.core.CommonSerializable
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable(with = CoreDateTimeSerializer::class)
actual data class CoreDateTime actual constructor(
    actual val value: LocalDateTime
) : CommonSerializable