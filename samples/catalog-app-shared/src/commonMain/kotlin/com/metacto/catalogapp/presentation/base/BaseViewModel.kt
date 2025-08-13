package com.metacto.catalogapp.presentation.base

import com.metacto.core.ui.base.CoreViewModel
import com.metacto.core.ui.base.ErrorType
import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState
import com.metacto.core.ui.globalState.models.LoadingType
import com.metacto.catalogapp.loggers.ICrashLogger
import com.metacto.catalogapp.presentation.app.globalState.IAppGlobalState
import org.koin.core.component.inject

abstract class BaseViewModel<S : ViewState, E : ViewEvent, SF : ViewSideEffect> :
    CoreViewModel<S, E, SF>() {

    protected val globalState by inject<IAppGlobalState>()
    private val crashLogger by inject<ICrashLogger>()
    private val viewModelName by lazy { this::class.simpleName.orEmpty() }

    override val defaultLoadingType: LoadingType = LoadingType.SecondaryCircularBlocking

    override val defaultErrorType: ErrorType = ErrorType.SnackBar

    override fun onReceiveEvent(event: E) {
        super.onReceiveEvent(event)
        crashLogger.logEvent("$viewModelName.$event")
    }

    override fun onChangeState(newState: S) {
        super.onChangeState(newState)
        crashLogger.setCustomKey("UIState", newState.toString())
    }

    override fun onSendSideEffect(effect: SF) {
        super.onSendSideEffect(effect)
        crashLogger.logEvent("$viewModelName.$effect")
    }
}