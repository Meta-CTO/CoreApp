package com.metacto.core.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.metacto.core.viewModelsStore.ViewModelsStore
import org.koin.core.component.inject

@Composable
actual inline fun <reified VM : CoreViewModel<*, *, *>> BaseScreen<*>.rememberViewModel(): VM {
    val viewModelsStore by inject<ViewModelsStore>()
    val viewModelKey = "$key:$tag"

    return remember {
        viewModelsStore.getOrPut(viewModelKey) {
            inject<VM>().value
        } as VM
    }
}

actual inline fun <reified VM : CoreViewModel<*, *, *>> BaseScreen<*>.getViewModel(): VM {
    val viewModelsStore by inject<ViewModelsStore>()
    val viewModelKey = "$key:$tag"

    return viewModelsStore.getOrPut(viewModelKey) {
        inject<VM>().value
    } as VM
}

fun BaseScreen<*>.onDispose() {
    val viewModelKey = "$key:$tag"
    val viewModelsStore by inject<ViewModelsStore>()

    viewModelsStore.remove(viewModelKey)
}