package com.metacto.core.presentation.base

import androidx.compose.runtime.Composable

abstract class BaseSheet<T : CoreViewModel<*, *, *>> : BaseScreen<T>() {

    @Composable
    override fun Content() {}
}