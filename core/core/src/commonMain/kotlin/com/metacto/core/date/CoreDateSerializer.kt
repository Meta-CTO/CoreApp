package com.metacto.core.date

import kotlinx.datetime.LocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object CoreDateSerializer : KSerializer<CoreDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("CoreDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: CoreDate) {
        encoder.encodeString(value.value.toString())
    }

    override fun deserialize(decoder: Decoder): CoreDate {
        return CoreDate(LocalDate.parse(decoder.decodeString()))
    }
}