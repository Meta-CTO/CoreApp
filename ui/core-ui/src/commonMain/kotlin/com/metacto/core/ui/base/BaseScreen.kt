package com.metacto.core.ui.base

import androidx.compose.runtime.Composable
import com.metacto.core.ui.extensions.OnLifecycleEvent
import com.metacto.core.ui.navigation.NavManager
import org.koin.core.component.inject

abstract class BaseScreen<T : CoreViewModel<*, *, *>> : CoreScreen() {
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