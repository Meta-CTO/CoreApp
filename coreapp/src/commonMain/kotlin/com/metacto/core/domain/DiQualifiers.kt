package com.metacto.core.domain

import org.koin.core.qualifier.named

internal object DiQualifiers {
    val videoPlayerManagers = named("videoPlayerManagers")
    val audioPlayerManagers = named("audioPlayerManagers")
}