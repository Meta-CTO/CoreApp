package com.metacto.core.date

import com.metacto.kmm.core.CommonSerializable
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable(with = CoreDateSerializer::class)
expect class CoreDate(
    value: LocalDate
) : CommonSerializable {
    val value: LocalDate
    
    companion object
}