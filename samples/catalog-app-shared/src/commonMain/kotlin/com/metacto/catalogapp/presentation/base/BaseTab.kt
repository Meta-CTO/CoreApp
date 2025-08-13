package com.metacto.catalogapp.presentation.base

import com.metacto.core.ui.base.CoreTab
import com.metacto.catalogapp.crash.CrashLogger
import org.koin.core.component.inject
import kotlin.jvm.Transient

abstract class BaseTab<VM : BaseViewModel<*, *, *>> : CoreTab<VM>() {

    @delegate:Transient
    private val crashLogger by inject<CrashLogger>()
    private val tabName by lazy { this::class.simpleName.orEmpty() }

    override fun onDisplayed() {
        super.onDisplayed()
        crashLogger.logEvent("Tab displayed: $tabName")
    }
}