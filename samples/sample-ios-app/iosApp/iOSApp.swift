import SwiftUI
import appShared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) private var appDelegate: AppDelegate
    private let deepLinkManager: IDeepLinkManager
    
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
        
        // Then inject deep link manager
        deepLinkManager = DiProvider().get(clazz: IDeepLinkManagerKt.Class) as IDeepLinkManager
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .onOpenURL { url in
                    deepLinkManager.emitDeepLink(link: url.absoluteString)
                }.onContinueUserActivity(NSUserActivityTypeBrowsingWeb) { activity in
                    if let url = activity.webpageURL?.absoluteString {
                        deepLinkManager.emitDeepLink(link: url)
                    }
                }
        }
    }
}
