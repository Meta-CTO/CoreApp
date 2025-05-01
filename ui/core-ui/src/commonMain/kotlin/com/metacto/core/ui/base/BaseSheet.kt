package com.metacto.core.ui.base

import androidx.compose.runtime.Composable

abstract class BaseSheet<T : CoreViewModel<*, *, *>> : BaseScreen<T>() {

    @Composable
    override fun Content() {}
}