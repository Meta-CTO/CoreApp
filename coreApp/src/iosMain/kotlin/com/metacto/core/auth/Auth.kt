package com.metacto.core.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import com.swensonhe.strapikmm.auth.AuthOptions
import dev.gitlive.firebase.auth.PhoneVerificationProvider

@Composable
actual fun rememberAuthOptions(onCancelled: () -> Unit): AuthOptions {
    val presentingViewController = LocalUIViewController.current
    return remember {
         AuthOptions(presentingViewController = presentingViewController)
    }
}

@Composable
actual fun rememberPhoneVerificationProvider(): PhoneVerificationProvider {
    return remember {
        object : PhoneVerificationProvider {
        }
    }
}