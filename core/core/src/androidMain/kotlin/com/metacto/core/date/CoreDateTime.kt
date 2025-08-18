package com.metacto.core.date

import com.metacto.kmm.core.CommonSerializable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

@Serializable(with = CoreDateTimeSerializer::class)
actual class CoreDateTime actual constructor(
    actual val value: LocalDateTime
) : CommonSerializable {

    private fun writeObject(out: ObjectOutputStream) {
        val millis = value.toInstant(TimeZone.UTC).toEpochMilliseconds()
        out.writeLong(millis)
    }

    private fun readObject(inp: ObjectInputStream) {
        val millis = inp.readLong()
        val instant = kotlinx.datetime.Instant.fromEpochMilliseconds(millis)
        val localDateTime = instant.toLocalDateTime(TimeZone.UTC)

        val field = this::class.java.getDeclaredField("value")
        field.isAccessible = true
        field.set(this, localDateTime)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CoreDateTime) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "CoreDateTime(value=$value)"
    }

    actual companion object {
        private const val serialVersionUID = 1L
    }
}