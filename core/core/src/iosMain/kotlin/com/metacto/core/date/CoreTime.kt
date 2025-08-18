package com.metacto.core.date

import com.metacto.kmm.core.CommonSerializable
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable(with = CoreTimeSerializer::class)
actual data class CoreTime actual constructor(
    actual val value: LocalTime
) : CommonSerializable {
    
    actual companion object
}