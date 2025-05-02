package com.metacto.playground.presentation.base

import com.metacto.core.ui.base.CoreViewModel
import com.metacto.core.ui.base.ErrorType
import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState
import com.metacto.core.ui.globalState.models.LoadingType
import com.metacto.playground.presentation.app.globalState.IAppGlobalState
import org.koin.core.component.inject

abstract class BaseViewModel<S : ViewState, E : ViewEvent, SF : ViewSideEffect> :
    CoreViewModel<S, E, SF>() {

    protected val globalState by inject<IAppGlobalState>()

    override val defaultLoadingType: LoadingType = LoadingType.SecondaryCircularBlocking

    override val defaultErrorType: ErrorType = ErrorType.SnackBar
}