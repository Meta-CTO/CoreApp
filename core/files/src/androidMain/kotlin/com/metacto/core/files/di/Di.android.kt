package com.metacto.core.files.di

import com.metacto.core.files.FileManager
import com.metacto.core.files.IFileManager
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // Add Android specific dependencies here

    single<IFileManager> {
        FileManager()
    }
}