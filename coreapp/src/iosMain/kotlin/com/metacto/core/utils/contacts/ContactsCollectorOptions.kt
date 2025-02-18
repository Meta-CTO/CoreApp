package com.metacto.core.utils.contacts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.metacto.strapikmm.contact.ContactsDataCollectorOptions

actual class ContactsCollectorOptionsFactory {
    actual fun createOptions(): ContactsDataCollectorOptions {
        return ContactsDataCollectorOptions()
    }
}

@Composable
actual fun rememberContactsCollectorOptionsFactory(): ContactsCollectorOptionsFactory {
    return remember {
        ContactsCollectorOptionsFactory()
    }
}