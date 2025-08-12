package com.metacto.core.ui.base

abstract class BaseTab<T : CoreViewModel<*, *, *>> : CoreScreen() {

    open fun onDisplayed() {
    }

    open fun onHidden() {
    }

    open fun onNavBarTabClicked() {
    }
}
