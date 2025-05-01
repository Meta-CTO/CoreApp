package com.metacto.core.ui.di

import com.metacto.core.ui.base.CommonViewModel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

expect inline fun <reified T : CommonViewModel> Module.commonViewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>