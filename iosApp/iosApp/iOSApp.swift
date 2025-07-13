import SwiftUI
import Shared

@main
struct iOSApp: App {

    init() {
        KoinKt.doInitKoinApplication()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
