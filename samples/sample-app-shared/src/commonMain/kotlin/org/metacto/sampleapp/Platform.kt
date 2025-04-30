package org.metacto.sampleapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform