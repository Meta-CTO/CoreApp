package com.sampleApp.app.presentation.components

import com.metacto.core.presentation.base.CoreViewModel
import com.metacto.core.presentation.base.ErrorType
import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState
import com.metacto.core.presentation.globalState.models.LoadingType
import com.sampleApp.app.presentation.app.globalState.IAppGlobalState
import org.koin.core.component.inject

abstract class BaseViewModel<S : ViewState, E : ViewEvent, SF : ViewSideEffect> :
    CoreViewModel<S, E, SF>() {

    protected val globalState by inject<IAppGlobalState>()

    override val defaultLoadingType: LoadingType = LoadingType.SecondaryCircularBlocking

    override val defaultErrorType: ErrorType = ErrorType.SnackBar
}