package com.metacto.core.auth

import androidx.compose.runtime.Composable
import com.metacto.strapikmm.auth.AuthOptions
import dev.gitlive.firebase.auth.PhoneVerificationProvider

expect class AuthOptionsFactory {
    fun createAuthOptions(onCancelled: () -> Unit): AuthOptions
}

@Composable
expect fun rememberAuthOptionsFactory(): AuthOptionsFactory

@Composable
expect fun rememberPhoneVerificationProvider(): PhoneVerificationProvider