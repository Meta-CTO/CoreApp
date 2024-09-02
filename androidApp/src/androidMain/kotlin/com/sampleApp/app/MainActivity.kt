package com.sampleApp.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.metacto.core.utils.deepLink.IDeepLinkManager
import com.mmk.kmpnotifier.extensions.onCreateOrOnNewIntent
import com.mmk.kmpnotifier.notification.NotifierManager
import com.sampleApp.app.presentation.MainView
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private val deepLinkManager by inject<IDeepLinkManager>()
    private val notifier by inject<NotifierManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotifierManager.onCreateOrOnNewIntent(intent)

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
        notifier.onCreateOrOnNewIntent(intent)
    }

    private fun checkDeepLink(intent: Intent) {
        intent.dataString?.let {
            deepLinkManager.emitDeepLink(it)
        }
    }
}