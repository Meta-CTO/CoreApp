package com.metacto.catalogapp.presentation.base

import androidx.compose.runtime.Composable
import com.metacto.core.ui.base.CoreSheet
import com.metacto.catalogapp.crash.CrashLogger
import org.koin.core.component.inject
import kotlin.jvm.Transient

abstract class BaseSheet<VM : BaseViewModel<*, *, *>> : CoreSheet<VM>() {

    @delegate:Transient
    private val crashLogger by inject<CrashLogger>()
    private val sheetName by lazy { this::class.simpleName.orEmpty() }
    
    @Composable
    abstract override fun Content()

    override fun onDisplayed() {
        super.onDisplayed()
        crashLogger.logEvent("Sheet displayed: $sheetName")
    }
}