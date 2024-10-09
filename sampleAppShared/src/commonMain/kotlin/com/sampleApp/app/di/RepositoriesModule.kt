package com.sampleApp.app.di

import com.metacto.core.domain.repos.UploadRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single {
        UploadRepository(get(), get(), get())
    }
}