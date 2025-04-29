package com.metacto.core.common.extensions

fun String.cleanFilePath(): String {
    return this
        .removePrefix("file://")
        .replace("%20", " ")
}