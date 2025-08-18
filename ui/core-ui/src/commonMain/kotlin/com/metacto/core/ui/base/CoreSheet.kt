package com.metacto.core.ui.base

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import com.metacto.core.extensions.randomUUID
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class CoreSheet<T : CoreViewModel<*, *, *>> : Screen, KoinComponent {
    override val key: ScreenKey = randomUUID()
    open val viewModelTag: String = randomUUID()
    open val screenTag: String = randomUUID()

    open fun onDisplayed() {
    }

    open fun onHidden() {
    }

    @Composable
    override fun Content() {
    }
}

@Composable
inline fun <reified VM : CoreViewModel<*, *, *>> CoreSheet<*>.rememberViewModel(): VM {
    return rememberScreenModel(viewModelTag) {
        inject<VM>().value
    }
}

inline fun <reified VM : CoreViewModel<*, *, *>> CoreSheet<*>.getViewModel(): VM {
    return inject<VM>().value
}