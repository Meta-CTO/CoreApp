package com.metacto.core.utils.extensions

fun String.cleanFilePath(): String {
    return this
        .removePrefix("file://")
        .replace("%20", " ")
}