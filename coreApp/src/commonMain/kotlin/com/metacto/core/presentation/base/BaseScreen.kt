package com.metacto.core.presentation.base

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import com.metacto.core.utils.extensions.randomUUID
import com.metacto.core.utils.navigation.INavManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseScreen<T : CoreViewModel<*, *, *>> : Screen, KoinComponent {

    @Composable
    protected inline fun <reified T : CoreViewModel<*, *, *>> rememberViewModel(): T {
        return rememberScreenModel(tag) {
            inject<T>().value
        }
    }

    protected inline fun <reified T : CoreViewModel<*, *, *>> getViewModel(): T {
        return inject<T>().value
    }

    protected fun getNavManager(): INavManager {
        return inject<INavManager>().value
    }

    open fun onBackPressed() {
        getNavManager().goBack()
    }

    override val key: ScreenKey = randomUUID()

    protected open val tag: String = randomUUID()
}
