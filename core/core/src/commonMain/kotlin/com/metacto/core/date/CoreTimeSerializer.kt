package com.metacto.core.date

import kotlinx.datetime.LocalTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object CoreTimeSerializer : KSerializer<CoreTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("CoreTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: CoreTime) {
        encoder.encodeString(value.value.toString())
    }

    override fun deserialize(decoder: Decoder): CoreTime {
        return CoreTime(LocalTime.parse(decoder.decodeString()))
    }
}