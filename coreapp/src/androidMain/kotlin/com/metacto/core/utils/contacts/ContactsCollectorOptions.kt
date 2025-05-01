package com.metacto.core.utils.contacts

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.metacto.core.ui.extensions.getActivity
import com.metacto.strapikmm.contact.ContactsDataCollectorOptions

actual class ContactsCollectorOptionsFactory(
    private val context: Context,
    private val launcher: ActivityResultLauncher<String>
) {
    private var currentOptions: ContactsDataCollectorOptions? = null

    actual fun createOptions(): ContactsDataCollectorOptions {
        currentOptions = ContactsDataCollectorOptions(
            context = context.getActivity()!!,
            launcher = launcher
        )

        return currentOptions!!
    }

    internal fun onResult(result: Boolean) {
        currentOptions?.onResult?.invoke(result)
    }
}

@Composable
actual fun rememberContactsCollectorOptionsFactory(): ContactsCollectorOptionsFactory {
    // Init the factory
    var factory: ContactsCollectorOptionsFactory? = null
    val context = LocalContext.current

    // Create the activity launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            factory?.onResult(it)
        }
    )

    // Create or get the factory
    factory = remember {
        ContactsCollectorOptionsFactory(
            context = context,
            launcher = launcher
        )
    }

    // Then return it
    return factory
}