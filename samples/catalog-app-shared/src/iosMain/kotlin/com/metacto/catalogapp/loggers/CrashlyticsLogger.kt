package com.metacto.catalogapp.loggers

class CrashlyticsLogger : ICrashLogger {

    private fun isEnhancedLoggingEnabled(): Boolean {
        // iOS implementation - for now, always return true
        // TODO: Implement Firebase Remote Config for iOS
        return true
    }

    override fun setCurrentScreen(screenName: String) {
        // iOS implementation - logging to console for now
        println("Current Screen: $screenName")
    }

    override fun logEvent(eventName: String, parameters: Map<String, Any>) {
        if (!isEnhancedLoggingEnabled()) return

        // iOS implementation - logging to console for now
        if (parameters.isEmpty()) {
            println("Event: $eventName")
        } else {
            println("Event: $eventName with parameters: $parameters")
        }
    }

    override fun logException(exception: Throwable) {
        if (!isEnhancedLoggingEnabled()) return
        
        // iOS implementation - logging to console for now
        println("Exception: ${exception.message}")
        exception.printStackTrace()
    }

    override fun setUserId(userId: String) {
        // iOS implementation - logging to console for now
        println("User ID: $userId")
    }

    override fun setCustomKey(key: String, value: String) {
        // iOS implementation - logging to console for now
        println("Custom Key: $key = $value")
    }

    override fun clear() {
        // iOS implementation - logging to console for now
        println("Cleared user data")
    }
}