package com.sampleApp.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.metacto.core.presentation.components.inputFields.OutlinedOtpInputField
import com.sampleApp.app.presentation.MainView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            //Preview()
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
    }
}

@Preview
@Composable
private fun Preview() {
    OutlinedOtpInputField(
        text = "123",
        pinCount = 6,
        modifier = Modifier.width(200.dp)
    )
}