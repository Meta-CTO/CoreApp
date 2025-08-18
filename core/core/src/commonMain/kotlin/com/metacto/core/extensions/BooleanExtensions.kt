package com.metacto.core.extensions

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true

fun Boolean?.isNullOrFalse() = this == null || this == false