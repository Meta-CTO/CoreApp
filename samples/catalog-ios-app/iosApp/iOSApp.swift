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
        
        Koin_iosKt.doInitKoin(
            environment: environment,
            viewsFactory: ViewsFactory(),
            crashLogger: { () -> ICrashLogger in
                return CrashlyticsLogger()
            }
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .onOpenURL { url in
                    DiProvider.deepLinkManager.emitDeepLink(link: url.absoluteString)
                }.onContinueUserActivity(NSUserActivityTypeBrowsingWeb) { activity in
                    if let url = activity.webpageURL?.absoluteString {
                        DiProvider.deepLinkManager.emitDeepLink(link: url)
                    }
                }
        }
    }
}
