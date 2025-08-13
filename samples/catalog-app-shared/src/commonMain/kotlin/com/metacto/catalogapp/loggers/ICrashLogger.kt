package com.metacto.catalogapp.loggers

interface ICrashLogger {
    fun setCurrentScreen(screenName: String)
    fun logEvent(eventName: String, parameters: Map<String, Any> = emptyMap())
    fun logException(exception: Throwable)
    fun setUserId(userId: String)
    fun setCustomKey(key: String, value: String)
    fun clear()
}