package com.metacto.core.date

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object CoreDateTimeSerializer : KSerializer<CoreDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("CoreDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: CoreDateTime) {
        val instant = value.value.toInstant(TimeZone.UTC)
        encoder.encodeString(instant.toString())
    }

    override fun deserialize(decoder: Decoder): CoreDateTime {
        val isoString = decoder.decodeString()
        val instant = Instant.parse(isoString)
        return CoreDateTime(instant.toLocalDateTime(TimeZone.UTC))
    }
}