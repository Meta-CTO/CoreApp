package com.metacto.sampleapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform