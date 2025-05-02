package com.metacto.sampleapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.metacto.sampleapp.presentation.app.app.AppContent
import com.metacto.sampleapp.presentation.app.app.AppContract.Event
import com.metacto.sampleapp.presentation.app.app.AppViewModel
import org.koin.android.ext.android.inject

class MainActivity : FragmentActivity() {
    private val appViewModel by inject<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Config activity
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        appViewModel.setEvent(Event.Init)

        // Render app content
        setContent {
            AppContent()
        }
    }
}