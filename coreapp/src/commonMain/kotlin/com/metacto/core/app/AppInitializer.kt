package com.metacto.core.app

import com.metacto.core.CoreEnvironment


object AppInitializer {
    fun onApplicationStart(coreEnvironment: CoreEnvironment) {
        onApplicationStartPlatformSpecific(coreEnvironment)
    }
}