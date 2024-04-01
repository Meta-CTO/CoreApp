package com.metacto.core.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.metacto.core.utils.extensions.getActivity
import com.metacto.strapikmm.auth.AuthOptions
import dev.gitlive.firebase.auth.PhoneVerificationProvider
import java.util.concurrent.TimeUnit

@Composable
actual fun rememberAuthOptions(onCancelled: () -> Unit): AuthOptions {
    var authOptions: AuthOptions? = null
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            authOptions?.onResult?.invoke(it)
        }
    )

    authOptions = remember {
        AuthOptions(
            activity = context.getActivity()!!,
            launcher = launcher,
            onCanceled = {
                onCancelled.invoke()
            }
        )
    }

    return authOptions
}

@Composable
actual fun rememberPhoneVerificationProvider(): PhoneVerificationProvider {
    val activity = LocalContext.current.getActivity()!!
    return remember {
        object : PhoneVerificationProvider {
            override val activity: Activity = activity
            override val timeout: Long = 60
            override val unit: TimeUnit = TimeUnit.SECONDS

            override fun onCodeSent(verificationId: String, triggerResend: () -> Unit) {
            }

            override suspend fun getVerificationCode(): String {
                return ""
            }
        }
    }
}