package com.metacto.core.presentation.base

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import com.metacto.core.navigation.NavManager
import com.metacto.core.utils.extensions.randomUUID
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseScreen<T : CoreViewModel<*, *, *>> : Screen, KoinComponent {

    protected fun getNavManager(): NavManager {
        return inject<NavManager>().value
    }

    open fun onBackPressed() {
        getNavManager().goBack()
    }

    override val key: ScreenKey = randomUUID()

    open val tag: String = randomUUID()
}

@Composable
expect inline fun <reified VM : CoreViewModel<*, *, *>> BaseScreen<*>.rememberViewModel(): VM

expect inline fun <reified VM : CoreViewModel<*, *, *>> BaseScreen<*>.getViewModel(): VM