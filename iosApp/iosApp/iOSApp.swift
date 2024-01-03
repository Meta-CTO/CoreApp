import SwiftUI
import FirebaseCore
import GoogleSignIn
import FirebaseAuth

import sampleAppShared

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {
        initialize(application: application)
        return true
    }
    
    private func initialize(application: UIApplication) {
        initializeFirebase()
        registerForRemoteNotifications(application: application)
    }

    
    private func initializeFirebase() {
        FirebaseApp.configure()
    }
        
    private func registerForRemoteNotifications(application: UIApplication) {
        application.registerForRemoteNotifications()
    }
    
    func application(
        _ application: UIApplication,
        didReceiveRemoteNotification userInfo: [AnyHashable: Any]
    ) async -> UIBackgroundFetchResult {
        if Auth.auth().canHandleNotification(userInfo) {
            // Do nothing Auth handles our userInfo
        }
        
        return .noData
    }
}

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) private var appDelegate: AppDelegate

    init() {
        // TODO: check current build type and pass suitable values
        KoinKt.doInitKoin(
            environment: AppEnvironment().dev()
        )
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
//                .onOpenURL { url in
//                    DeepLinkRegistryKt.process(deepLink: url.absoluteString)
//                }.onContinueUserActivity(NSUserActivityTypeBrowsingWeb) { activity in
//                    DeepLinkRegistryKt.process(deepLink: activity.webpageURL?.absoluteString ?? "")
//                }
		}
    }
}
