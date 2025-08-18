package com.metacto.catalogapp.crash

interface FirebaseCrashlytics {
    fun setCustomKey(key: String, value: String)
    fun setUserId(userId: String)
    fun log(message: String)
    fun recordException(exception: Throwable)
}