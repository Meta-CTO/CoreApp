package com.metacto.core.date

import com.metacto.kmm.core.CommonSerializable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

@Serializable(with = CoreDateSerializer::class)
actual class CoreDate actual constructor(
    actual val value: LocalDate
) : CommonSerializable {

    private fun writeObject(out: ObjectOutputStream) {
        val millis = value.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
        out.writeLong(millis)
    }

    private fun readObject(inp: ObjectInputStream) {
        val millis = inp.readLong()
        val instant = kotlinx.datetime.Instant.fromEpochMilliseconds(millis)
        val localDate = instant.toLocalDateTime(TimeZone.UTC).date

        val field = this::class.java.getDeclaredField("value")
        field.isAccessible = true
        field.set(this, localDate)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CoreDate) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "CoreDate(value=$value)"
    }

    actual companion object {
        private const val serialVersionUID = 1L
    }
}