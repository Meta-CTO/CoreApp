package com.metacto.core.date

import com.metacto.kmm.core.CommonSerializable
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable(with = CoreDateSerializer::class)
actual data class CoreDate actual constructor(
    actual val value: LocalDate
) : CommonSerializable {
    
    actual companion object
}