package com.metacto.kmm.fileManager.di

import org.koin.core.context.startKoin

fun initKoin() = startKoin {
    modules(
        fileManagerPlatformModule()
    )
}
