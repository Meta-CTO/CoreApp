package com.metacto.core.auth

import androidx.compose.runtime.Composable
import com.swensonhe.strapikmm.auth.AuthOptions
import dev.gitlive.firebase.auth.PhoneVerificationProvider

@Composable
expect fun rememberAuthOptions(onCancelled: () -> Unit = {}): AuthOptions

@Composable
expect fun rememberPhoneVerificationProvider(): PhoneVerificationProvider