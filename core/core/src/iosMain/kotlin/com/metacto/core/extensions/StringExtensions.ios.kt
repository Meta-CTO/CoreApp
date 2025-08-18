package com.metacto.core.extensions

fun String.cleanFilePath(): String {
    return this
        .removePrefix("file://")
        .replace("%20", " ")
}