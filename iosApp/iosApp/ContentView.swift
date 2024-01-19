import SwiftUI
import sampleAppShared

struct ContentView: View {
    var body: some View {
        VStack {
            AvoidDisposeViewControllerRepresentable(
                composeController: Main_iosKt.MainViewController()
            ).ignoresSafeArea(.all)
        }
    }
}
