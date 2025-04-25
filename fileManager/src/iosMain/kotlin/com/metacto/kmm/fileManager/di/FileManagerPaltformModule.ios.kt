package com.metacto.kmm.fileManager.di

import com.metacto.kmm.fileManager.FileManager
import com.metacto.kmm.fileManager.IFileManager
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun fileManagerPlatformModule(): Module = module {
    single<IFileManager> {
        FileManager()
    }
}