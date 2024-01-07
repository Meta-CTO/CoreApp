package com.metacto.core.presentation.base

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import org.koin.core.component.inject

@Composable
actual inline fun <reified VM : CoreViewModel<*, *, *>> BaseScreen<*>.rememberViewModel(): VM {
    return rememberScreenModel(tag) {
        inject<VM>().value
    }
}

actual inline fun <reified VM : CoreViewModel<*, *, *>> BaseScreen<*>.getViewModel(): VM {
    return inject<VM>().value
}