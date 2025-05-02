package com.metacto.sampleapp

import android.app.Application
import com.metacto.sampleapp.constants.AppEnvironment
import com.metacto.sampleapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Prepare environment
        val environment = if (BuildConfig.FLAVOR == "qa") {
            AppEnvironment.Dev
        } else {
            AppEnvironment.Prod
        }

        // Init koin
        initKoin(environment) {
            androidContext(this@SampleApp)
        }
    }
}