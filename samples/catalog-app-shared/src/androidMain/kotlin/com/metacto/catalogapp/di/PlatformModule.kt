package com.metacto.catalogapp.di

import com.metacto.catalogapp.utils.FileHandler
import org.koin.dsl.module

actual val platformModule = module {
    // Define android specific dependencies here
    single {
        FileHandler(get())
    }
}