package com.metacto.core.utils.contacts

import androidx.compose.runtime.Composable
import com.metacto.strapikmm.contact.ContactsDataCollectorOptions

expect class ContactsCollectorOptionsFactory {
    fun createOptions(): ContactsDataCollectorOptions
}

@Composable
expect fun rememberContactsCollectorOptionsFactory(): ContactsCollectorOptionsFactory