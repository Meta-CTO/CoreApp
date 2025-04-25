package com.metacto.kmm.utils.extensions

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
@ExperimentalUnsignedTypes
fun NSData.toByteArray(): ByteArray = ByteArray(length.toInt()).apply {
				usePinned { memcpy(it.addressOf(0), bytes, length) }
}