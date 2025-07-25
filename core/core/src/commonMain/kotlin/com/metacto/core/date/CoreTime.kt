package com.metacto.core.date

import com.metacto.kmm.core.CommonSerializable
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable(with = CoreTimeSerializer::class)
expect class CoreTime(
    value: LocalTime
) : CommonSerializable {
    val value: LocalTime
    
    companion object
}