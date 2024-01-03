package com.metacto.core.di

import com.metacto.core.presentation.base.CommonViewModel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

expect fun corePlatformModule(appStorageName: String): Module

expect inline fun <reified T : CommonViewModel> Module.commonViewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>