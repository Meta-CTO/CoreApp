package com.metacto.core.ui.base

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import com.metacto.core.extensions.randomUUID
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class CoreTab<T : CoreViewModel<*, *, *>> : Screen, KoinComponent {
    override val key: ScreenKey = randomUUID()
    open val viewModelTag: String = randomUUID()
    open val screenTag: String = randomUUID()

    open fun onDisplayed() {
    }

    open fun onHidden() {
    }

    open fun onNavBarTabClicked() {
    }
}

@Composable
inline fun <reified VM : CoreViewModel<*, *, *>> CoreTab<*>.rememberViewModel(): VM {
    return rememberScreenModel(viewModelTag) {
        inject<VM>().value
    }
}

inline fun <reified VM : CoreViewModel<*, *, *>> CoreTab<*>.getViewModel(): VM {
    return inject<VM>().value
}