package com.metacto.core.auth

import androidx.compose.runtime.Composable
import com.metacto.strapikmm.auth.AuthOptions
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import dev.gitlive.firebase.auth.PhoneVerificationProvider

@Composable
actual fun rememberAuthOptions(onCancelled: () -> Unit): AuthOptions {
    val currentViewController = LocalUIViewController.current
    return remember {
        return@remember AuthOptions(
            presentingViewController = currentViewController
        )
    }
}

@Composable
actual fun rememberPhoneVerificationProvider(): PhoneVerificationProvider {
    return remember {
        object : PhoneVerificationProvider {
        }
    }
}