package com.sampleApp.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.metacto.core.notifications.INotificationManager
import com.metacto.core.utils.deepLink.IDeepLinkManager
import com.sampleApp.app.presentation.MainView
import com.sampleApp.app.presentation.app.app.AppContract
import com.sampleApp.app.presentation.app.app.AppViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val deepLinkManager by inject<IDeepLinkManager>()
    private val notificationManager by inject<INotificationManager>()
    private val appViewModel by inject<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationManager.onCreateOrOnNewIntent(intent)
        appViewModel.setEvent(AppContract.Event.Init)

        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MainView()
        }

        intent?.let { checkDeepLink(intent) }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        checkDeepLink(intent)
        notificationManager.onCreateOrOnNewIntent(intent)
    }

    private fun checkDeepLink(intent: Intent) {
        intent.dataString?.let {
            deepLinkManager.emitDeepLink(it)
        }
    }
}