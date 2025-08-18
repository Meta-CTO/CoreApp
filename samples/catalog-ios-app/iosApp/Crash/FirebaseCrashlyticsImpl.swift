//
//  FirebaseCrashlyticsImpl.swift
//  iosApp
//
//  Created by Claude AI on 13/08/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import FirebaseCrashlytics
import appShared

class FirebaseCrashlyticsImpl: FirebaseCrashlytics {
    private let crashlytics = Crashlytics.crashlytics()
    
    func setCustomKey(key: String, value: String) {
        crashlytics.setCustomValue(value, forKey: key)
    }
    
    func setUserId(userId: String) {
        crashlytics.setUserID(userId)
    }
    
    func log(message: String) {
        crashlytics.log(message)
    }
    
    func recordException(exception: KotlinThrowable) {
        let nsError = NSError(
            domain: "com.metacto.catalogapp",
            code: 0,
            userInfo: [
                NSLocalizedDescriptionKey: exception.message ?? "Unknown error"
            ]
        )
        crashlytics.record(error: nsError)
    }
}