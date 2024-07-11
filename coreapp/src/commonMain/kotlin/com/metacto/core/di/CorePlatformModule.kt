package com.metacto.core.di

import com.metacto.core.presentation.base.CommonViewModel
import com.metacto.strapikmm.errorhandling.SerializableNetworkError
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

expect fun<T : SerializableNetworkError> corePlatformModule(
    appStorageName: String,
    shouldShowActualErrorMessages: Boolean,
    errorClass: KClass<T>
): Module

expect inline fun <reified T : CommonViewModel> Module.commonViewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>