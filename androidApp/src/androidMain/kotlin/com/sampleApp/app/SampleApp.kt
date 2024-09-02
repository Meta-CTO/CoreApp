package com.sampleApp.app

import android.app.Application
import com.metacto.core.app.AppInitializer
import com.sampleApp.app.constants.AppEnvironment
import com.sampleApp.app.di.initKoin
import org.koin.android.ext.koin.androidContext

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Prepare environment
        val environment = if (BuildConfig.FLAVOR == "qa") {
            AppEnvironment.dev()
        } else {
            AppEnvironment.prod()
        }

        // Init koin
        initKoin(
            environment = environment
        ) {
            androidContext(this@SampleApp)
        }

        // init core
        AppInitializer.onApplicationStart(environment)
    }
}