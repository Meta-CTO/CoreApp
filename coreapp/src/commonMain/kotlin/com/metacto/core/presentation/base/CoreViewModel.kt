package com.metacto.core.presentation.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.metacto.core.navigation.NavManager
import com.metacto.core.presentation.globalState.ICoreGlobalState
import com.metacto.core.presentation.globalState.models.ConfirmationPopupParams
import com.metacto.core.presentation.globalState.models.LoadingType
import com.metacto.core.presentation.globalState.models.MessagePopupParams
import com.metacto.core.presentation.globalState.models.SnackBarParams
import com.metacto.core.presentation.globalState.models.SnackBarType
import com.metacto.core.utils.IDispatchersProvider
import com.metacto.core.utils.IResourceProvider
import com.metacto.coreApp.MR
import com.swensonhe.strapikmm.datasource.network.services.strapi.JsonWithIgnoredUnknownKeys
import com.swensonhe.strapikmm.errorhandling.AppException
import com.swensonhe.strapikmm.errorhandling.NetworkErrorMapper
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.icerock.moko.resources.StringResource
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

interface ViewEvent

interface ViewState

interface ViewSideEffect

const val SIDE_EFFECTS_KEY = "side-effects_key"

expect open class CommonViewModel constructor()

abstract class CoreViewModel<S : ViewState, E : ViewEvent, SF : ViewSideEffect> :
    CommonViewModel(), ScreenModel, KoinComponent {

    // Inject main objects
    protected val coreGlobalState by inject<ICoreGlobalState>()
    protected val dispatcherProvider by inject<IDispatchersProvider>()
    protected val navManager by inject<NavManager>()
    protected val resourceProvider by inject<IResourceProvider>()

    abstract fun setInitialState(): S
    abstract fun handleEvents(event: E): Any

    private val initialState: S by lazy { setInitialState() }

    private val _viewState: MutableState<S> = mutableStateOf(initialState)
    val viewState: State<S> = _viewState
    val currentState: S get() = viewState.value

    private val _event: MutableSharedFlow<E> = MutableSharedFlow()

    private val _effect: Channel<SF> = Channel()
    val effect = _effect.receiveAsFlow()

    private var loadingCount = 0

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        coroutineScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    fun setEvent(event: E) {
        coroutineScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: S.() -> S) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setEffect(builder: () -> SF) {
        val effectValue = builder()
        coroutineScope.launch { _effect.send(effectValue) }
    }
    fun <T> executeCatching(
        block: suspend () -> T,
        loadingType: LoadingType = defaultLoadingType,
        scope: CoroutineScope = coroutineScope,
        context: CoroutineContext = dispatcherProvider.io,
        debounce: Long = 0,
        oldDebounceJob: Job? = null,
        onCreated: (Job) -> Unit = {},
        onError: ((Throwable, String?) -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
    ): Job {
        // Cancel old job if required
        if (debounce != 0L) oldDebounceJob?.cancel()

        // Create the new job
        val newJob = scope.launch(context) {
            // Debounce if required
            if (debounce != 0L) delay(debounce)

            val hasLoading = loadingType != LoadingType.NoLoading

            try {
                if (hasLoading) showLoading(loadingType)
                block.invoke()
                if (hasLoading) hideLoading()
            } catch (e: kotlin.coroutines.cancellation.CancellationException) {
                onError?.invoke(e, null)
            } catch (e: CancellationException) {
                onError?.invoke(e, null)
            } catch (throwable: Throwable) {
                if (isAuthError(throwable)) {
                    handleAuthError()
                    return@launch
                }
                val errorMessage = when (throwable) {
                    is AppException -> {
                        extractErrorCodeAndMessage(throwable.errorMessage).first
                    }

                    is SocketTimeoutException,
                    is HttpRequestTimeoutException,
                    is ConnectTimeoutException -> {
                        resourceProvider.getString(MR.strings.server_taking_too_long)
                    }

                    else -> {
                        NetworkErrorMapper().mapThrowable(throwable).errorMessage
                    }
                }
                if (hasLoading) hideLoading()
                showError(errorMessage)
                onError?.invoke(throwable, errorMessage)
            } finally {
                onComplete?.invoke()
            }
        }

        // Invoke on created and return the new job
        onCreated.invoke(newJob)
        return newJob
    }

    fun <T> executeSilent(
        block: suspend () -> T,
        scope: CoroutineScope = coroutineScope,
        context: CoroutineContext = dispatcherProvider.io,
        debounce: Long = 0,
        oldDebounceJob: Job? = null,
        onCreated: (Job) -> Unit = {},
        onError: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ): Job{
        // Cancel old job if required
        if (debounce != 0L) oldDebounceJob?.cancel()

        // Create the new job
        val newJob = scope.launch(context) {
            // Debounce if required
            if (debounce != 0L) delay(debounce)

            try {
                block.invoke()
            } catch (throwable: Throwable) {
                if (isAuthError(throwable)) {
                    handleAuthError()
                    return@launch
                }
                onError?.invoke()
            } finally {
                onComplete?.invoke()
            }
        }

        // Invoke on created and return the new job
        onCreated.invoke(newJob)
        return newJob
    }

    private fun isAuthError(throwable: Throwable): Boolean {
        return throwable is AppException && throwable.errorCode == 401
    }

    private fun handleAuthError() {
        coreGlobalState.confirmationPopup(
            ConfirmationPopupParams(
                title = resourceProvider.getString(MR.strings.session_expired),
                body = resourceProvider.getString(MR.strings.your_session_expired_login_again),
                positiveButtonText = resourceProvider.getString(MR.strings.ok),
                isCancellable = false,
                onPositiveClick = {
                    executeSilent({
                        Firebase.auth.signOut()
                        coreGlobalState.navigateToLogin()
                    })
                },
                onNegativeClick = {
                    coreGlobalState.navigateToLogin()
                }
            )
        )
    }

    open val defaultLoadingType: LoadingType = LoadingType.LottieBlocking()

    open fun showLoading(type: LoadingType = defaultLoadingType) {
        coreGlobalState.loading(type)
        loadingCount++
    }

    open fun hideLoading() {
        coreGlobalState.loading(LoadingType.NoLoading)
        loadingCount--
    }

    protected fun isLoading(): Boolean {
        return loadingCount > 0
    }

    protected fun showError(error: String) {
        when (getErrorMessageType()) {
            ErrorMessageType.Popup -> coreGlobalState.messagePopup(
                MessagePopupParams(
                    title = resourceProvider.getString(MR.strings.error),
                    body = error
                )
            )

            ErrorMessageType.SnackBar -> coreGlobalState.snackBar(
                SnackBarParams(
                    message = error,
                    type = SnackBarType.ERROR
                )
            )
        }
    }

    protected fun showError(errorRes: StringResource) {
        val error = resourceProvider.getString(errorRes)
        showError(error)
    }

    open fun getErrorMessageType(): ErrorMessageType {
        return ErrorMessageType.Popup
    }

    protected inline fun <reified D, reified R> NavManager.collectNavResult(
        crossinline onResult: (R) -> Unit) {
        executeSilent({
            // Get nav manager
            val navManager = this@collectNavResult

            // Collect results with view model scope
            navManager.onNavResult<D, R>(callback = onResult)
        })
    }
}

private fun extractErrorCodeAndMessage(jsonString: String): Pair<String, Int> {
    val jsonObject = JsonWithIgnoredUnknownKeys.parseToJsonElement(jsonString) as JsonObject
    val message = jsonObject["message"]?.jsonPrimitive?.content.orEmpty()
    val code = jsonObject["code"]?.jsonPrimitive?.content?.toInt() ?: 0
    return message to code
}