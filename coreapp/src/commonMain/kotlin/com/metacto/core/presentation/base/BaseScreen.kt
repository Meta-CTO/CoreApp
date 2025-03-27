package com.metacto.core.presentation.base

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import com.metacto.core.navigation.NavManager
import com.metacto.core.utils.extensions.randomUUID
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseScreen<T : CoreViewModel<*, *, *>> : Screen, KoinComponent {
    override val key: ScreenKey = randomUUID()
    open val viewModelTag: String = randomUUID()
    open val screenTag: String = randomUUID()
    open val enableSwipeToGoBack: Boolean = true

    protected fun getNavManager(): NavManager {
        return inject<NavManager>().value
    }

    open fun onBackPressed() {
        getNavManager().goBack()
    }
}

@Composable
inline fun <reified VM : CoreViewModel<*, *, *>> BaseScreen<*>.rememberViewModel(): VM {
    return rememberScreenModel(viewModelTag) {
        inject<VM>().value
    }
}

inline fun <reified VM : CoreViewModel<*, *, *>> BaseScreen<*>.getViewModel(): VM {
    return inject<VM>().value
}