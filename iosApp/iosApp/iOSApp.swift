import SwiftUI
import FirebaseCore
import GoogleSignIn
import FirebaseAuth
import AVFAudio
import FirebaseMessaging

import sampleAppShared

class AppDelegate: NSObject, UIApplicationDelegate {
    private let notificationManager =  DiProvider().get(clazz: INotificationManagerKt.Class) as INotificationManager
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        requestPIPBackgroundMode()
        FirebaseApp.configure()
        return true
    }
    
    private func requestPIPBackgroundMode() {
        let session = AVAudioSession.sharedInstance()
        do {
            try session.setCategory(.playback, mode: .moviePlayback)
        } catch let error {
            print(error.localizedDescription)
        }
    }
    
    func application(
        _ application: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
        Messaging.messaging().apnsToken = deviceToken
    }
    
    func application(
        _ application: UIApplication,
        didReceiveRemoteNotification userInfo: [AnyHashable : Any]
    ) async -> UIBackgroundFetchResult {
        if Auth.auth().canHandleNotification(userInfo) {}
        notificationManager.onApplicationDidReceiveRemoteNotification(userInfo: userInfo)
        return UIBackgroundFetchResult.newData
    }
}

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) private var appDelegate: AppDelegate
    
    init() {
        KoinKt.doInitKoin(
            environment: AppEnvironment().dev()
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
