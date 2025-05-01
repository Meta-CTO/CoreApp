package com.metacto.core.notifications.di

import com.metacto.core.notifications.NotificationsConfigs
import org.koin.core.module.Module
import org.koin.dsl.module

fun notificationsModule(configs: NotificationsConfigs) = module {
    // Common dependencies can be added here

    includes(platformModule())

    single { configs }
}

internal expect fun platformModule(): Module