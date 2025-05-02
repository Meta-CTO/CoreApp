package com.metacto.catalogapp.di

import com.metacto.catalogapp.utils.FileHandler
import org.koin.dsl.module

actual val platformModule = module {
    // Define android specific dependencies here

    ///TODO THIS FOR DEMO ONLY  JUST TO CREATE FILES TO GET PATHS
    single {
        FileHandler(get())
    }
}