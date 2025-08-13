//
//  CrashlyticsLogger.swift
//  iosApp
//
//  Created by Claude AI on 13/08/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import FirebaseCrashlytics
import appShared

class CrashlyticsLogger: ICrashLogger {
    private var crashlytics: Crashlytics {
        return Crashlytics.crashlytics()
    }
    
    func setCurrentScreen(screenName: String) {
        crashlytics.setCustomValue(screenName, forKey: "CurrentScreen")
    }
    
    func logEvent(eventName: String, parameters: [String : Any]) {
        if !isEnhancedLoggingEnabled() { return }
        
        if parameters.isEmpty {
            crashlytics.log(eventName)
        } else {
            crashlytics.log("\(eventName): \(parameters)")
        }
    }
    
    func logException(exception: KotlinThrowable) {
        if !isEnhancedLoggingEnabled() { return }
        
        let error = NSError(domain: "KotlinException", code: 0, userInfo: [
            NSLocalizedDescriptionKey: exception.message ?? "Unknown Kotlin exception"
        ])
        crashlytics.record(error: error)
    }
    
    func setUserId(userId: String) {
        crashlytics.setUserID(userId)
    }
    
    func setCustomKey(key: String, value: String) {
        crashlytics.setCustomValue(value, forKey: key)
    }
    
    func clear() {
        crashlytics.setUserID("")
    }
    
    private func isEnhancedLoggingEnabled() -> Bool {
        return DiProvider.remoteConfigs.getBoolean(key: AppConstants().CONFIG_ENABLE_ENHANCED_CRASHLYTICS)?.boolValue ?? false
    }
}