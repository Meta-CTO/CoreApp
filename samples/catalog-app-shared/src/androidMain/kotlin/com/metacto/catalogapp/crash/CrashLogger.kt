package com.metacto.catalogapp.crash

internal actual fun setupCrashHandler(onCrash: () -> Unit) {
    val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
        onCrash.invoke()
        defaultHandler?.uncaughtException(thread, exception)
    }
}