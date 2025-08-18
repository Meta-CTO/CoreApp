package com.metacto.playground

import android.app.Application
import com.metacto.playground.constants.AppEnvironment
import com.metacto.playground.di.initKoin
import org.koin.android.ext.koin.androidContext

class PlaygroundApp : Application() {
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
            androidContext(this@PlaygroundApp)
        }
    }
}