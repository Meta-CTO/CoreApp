package com.metacto.core.ui.base

import androidx.compose.runtime.Composable

abstract class BaseSheet<T : CoreViewModel<*, *, *>> : CoreScreen() {

    open fun onDisplayed() {
    }

    open fun onHidden() {
    }

    @Composable
    override fun Content() {}
}