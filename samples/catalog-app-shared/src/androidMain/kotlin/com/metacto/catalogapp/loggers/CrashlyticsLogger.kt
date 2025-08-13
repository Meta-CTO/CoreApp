package com.metacto.catalogapp.loggers

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.metacto.catalogapp.constants.AppConstants
import com.metacto.core.extensions.orFalse
import com.metacto.kmm.remoteconfig.common.RemoteConfigProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CrashlyticsLogger : ICrashLogger, KoinComponent {
    private val remoteConfigs by inject<RemoteConfigProvider>()
    private val crashlytics by lazy {
        FirebaseCrashlytics.getInstance()
    }

    override fun setCurrentScreen(screenName: String) {
        crashlytics.setCustomKey("CurrentScreen", screenName)
    }

    override fun logEvent(eventName: String, parameters: Map<String, Any>) {
        if (!isEnhancedLoggingEnabled()) return

        if (parameters.isEmpty()) {
            crashlytics.log(eventName)
        } else {
            crashlytics.log("$eventName: $parameters")
        }
    }

    override fun logException(exception: Throwable) {
        if (!isEnhancedLoggingEnabled()) return
        crashlytics.recordException(exception)
    }

    override fun setUserId(userId: String) {
        crashlytics.setUserId(userId)
    }

    override fun setCustomKey(key: String, value: String) {
        crashlytics.setCustomKey(key, value)
    }

    override fun clear() {
        crashlytics.setUserId("")
    }

    private fun isEnhancedLoggingEnabled(): Boolean {
        return remoteConfigs.getBoolean(AppConstants.CONFIG_ENABLE_ENHANCED_CRASHLYTICS).orFalse()
    }
}