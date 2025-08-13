package com.metacto.catalogapp.crash

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
internal actual fun setupCrashHandler(onCrash: () -> Unit) {
    // Handle crashes
    setUnhandledExceptionHook {
        onCrash.invoke()
        terminateWithUnhandledException(it)
    }
}