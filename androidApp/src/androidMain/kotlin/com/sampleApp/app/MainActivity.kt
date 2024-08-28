package com.sampleApp.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.metacto.core.utils.deepLink.IDeepLinkManager
import com.sampleApp.app.presentation.MainView
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val deepLinkManager by inject<IDeepLinkManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MainView()
        }

        intent?.let { checkDeepLink(intent) }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // check intent
        intent?.let { checkDeepLink(it) }
    }

    private fun checkDeepLink(intent: Intent) {
        intent.dataString?.let {
            deepLinkManager.emitDeepLink(it)
        }
    }
}