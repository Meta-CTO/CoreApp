package com.metacto.core.ui.base

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import com.metacto.core.extensions.randomUUID
import com.metacto.core.ui.extensions.OnLifecycleEvent
import com.metacto.core.ui.navigation.NavManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class CoreScreen<T : CoreViewModel<*, *, *>> : Screen, KoinComponent {
    override val key: ScreenKey = randomUUID()
    open val viewModelTag: String = randomUUID()
    open val screenTag: String = randomUUID()
    open val enableSwipeToGoBack: Boolean = true

    @Suppress("ComposableNaming")
    @Composable
    internal open fun trackLifecycle() {
        OnLifecycleEvent(
            onResume = ::onDisplayed,
            onPause = ::onHidden,
        )
    }

    protected fun getNavManager(): NavManager {
        return inject<NavManager>().value
    }

    open fun onDisplayed() {
    }

    open fun onHidden() {
    }

    open fun onBackPressed() {
        getNavManager().goBack()
    }
}

@Composable
inline fun <reified VM : CoreViewModel<*, *, *>> CoreScreen<*>.rememberViewModel(): VM {
    return rememberScreenModel(viewModelTag) {
        inject<VM>().value
    }
}

inline fun <reified VM : CoreViewModel<*, *, *>> CoreScreen<*>.getViewModel(): VM {
    return inject<VM>().value
}