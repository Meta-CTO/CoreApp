import SwiftUI
import appShared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) private var appDelegate: AppDelegate
    
    init() {
        // Prepare environment
        var environment: AppEnvironment
        #if DEBUG
        environment = AppEnvironment.Dev()
        #else
        environment = AppEnvironment.Prod()
        #endif
        
        KoinKt.doInitKoin(
            environment: environment
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .onOpenURL { url in
                
                }.onContinueUserActivity(NSUserActivityTypeBrowsingWeb) { activity in
                    if let url = activity.webpageURL?.absoluteString {
                    }
                }
        }
    }
}
