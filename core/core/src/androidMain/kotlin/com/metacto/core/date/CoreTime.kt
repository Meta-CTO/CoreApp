package com.metacto.core.date

import com.metacto.kmm.core.CommonSerializable
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

@Serializable(with = CoreTimeSerializer::class)
actual class CoreTime actual constructor(
    actual val value: LocalTime
) : CommonSerializable {

    private fun writeObject(out: ObjectOutputStream) {
        val nanoOfDay = value.toNanosecondOfDay()
        out.writeLong(nanoOfDay)
    }

    private fun readObject(inp: ObjectInputStream) {
        val nanoOfDay = inp.readLong()
        val localTime = LocalTime.fromNanosecondOfDay(nanoOfDay)

        val field = this::class.java.getDeclaredField("value")
        field.isAccessible = true
        field.set(this, localTime)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CoreTime) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "CoreTime(value=$value)"
    }

    actual companion object {
        private const val serialVersionUID = 1L
    }
}