package com.metacto.catalogapp.presentation.base

import androidx.compose.runtime.Composable
import com.metacto.core.ui.base.CoreScreen
import com.metacto.catalogapp.loggers.ICrashLogger
import org.koin.core.component.inject
import kotlin.jvm.Transient

abstract class BaseScreen<VM : BaseViewModel<*, *, *>> : CoreScreen<VM>() {

    @delegate:Transient
    private val crashLogger by inject<ICrashLogger>()
    private val screenName by lazy { this::class.simpleName.orEmpty() }
    
    @Composable
    abstract override fun Content()

    override fun onDisplayed() {
        super.onDisplayed()
        crashLogger.setCurrentScreen(screenName)
    }
}