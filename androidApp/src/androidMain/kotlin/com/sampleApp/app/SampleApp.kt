package com.sampleApp.app

import android.app.Application
import com.sampleApp.app.constants.OldAppEnvironment
import com.sampleApp.app.di.initKoin
import org.koin.android.ext.koin.androidContext

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Prepare environment
        val environment = if (BuildConfig.FLAVOR == "qa") {
            OldAppEnvironment.dev()
        } else {
            OldAppEnvironment.prod()
        }

        // Init koin
        initKoin(
            environment = environment
        ) {
            androidContext(this@SampleApp)
        }
    }
}