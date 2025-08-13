package com.metacto.catalogapp.crash

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.metacto.catalogapp.crash.FirebaseCrashlytics as FirebaseCrashlyticsInterface

class FirebaseCrashlyticsImpl : FirebaseCrashlyticsInterface {
    private val crashlytics by lazy { FirebaseCrashlytics.getInstance() }

    override fun setCustomKey(key: String, value: String) {
        crashlytics.setCustomKey(key, value)
    }

    override fun setUserId(userId: String) {
        crashlytics.setUserId(userId)
    }

    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun recordException(exception: Throwable) {
        crashlytics.recordException(exception)
    }
}