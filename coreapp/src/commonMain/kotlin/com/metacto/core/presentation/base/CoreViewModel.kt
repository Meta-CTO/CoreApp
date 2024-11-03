package com.metacto.core.presentation.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.metacto.core.domain.repos.forceUpdate.AppUpdateSource
import com.metacto.core.domain.repos.forceUpdate.ForceUpdateRepository
import com.metacto.core.navigation.NavManager
import com.metacto.core.permissions.IPermissionManager
import com.metacto.core.presentation.globalState.ICoreGlobalState
import com.metacto.core.presentation.globalState.models.ConfirmationPopupParams
import com.metacto.core.presentation.globalState.models.ForceUpdatePopupParams
import com.metacto.core.presentation.globalState.models.ItemPickerParams
import com.metacto.core.presentation.globalState.models.LoadingType
import com.metacto.core.presentation.globalState.models.MessagePopupParams
import com.metacto.core.presentation.globalState.models.SnackBarParams
import com.metacto.core.presentation.globalState.models.SnackBarType
import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.utils.CommonImageResource
import com.metacto.core.utils.IResourceProvider
import com.metacto.core.utils.PlatformType
import com.metacto.core.utils.asCommon
import com.metacto.core.utils.extensions.getPlatformType
import com.metacto.core.utils.launchers.IIntentLauncher
import com.metacto.coreApp.MR
import com.metacto.strapikmm.datasource.network.services.strapi.JsonWithIgnoredUnknownKeys
import com.metacto.strapikmm.errorhandling.AppException
import com.metacto.strapikmm.errorhandling.NetworkMapperConstants
import com.metacto.strapikmm.util.Logger
import dev.icerock.moko.resources.StringResource
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
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
    protected val navManager by inject<NavManager>()
    protected val resourceProvider by inject<IResourceProvider>()
    protected val logger by inject<Logger>()
    protected val forceUpdateRepository by inject<ForceUpdateRepository>()
    protected val intentLauncher by inject<IIntentLauncher>()
    val permissionManager by inject<IPermissionManager>()

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
    private var isObservingItemPicker: Boolean = false
    private var onPickerItemSelected: ((PickerItem) -> Unit)? = null

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        screenModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    fun setEvent(event: E) {
        screenModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: S.() -> S) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setEffect(builder: () -> SF) {
        val effectValue = builder()
        screenModelScope.launch { _effect.send(effectValue) }
    }

    open fun <T> executeCatching(
        block: suspend () -> T,
        loadingType: LoadingType = defaultLoadingType,
        errorType: ErrorType = defaultErrorType,
        scope: CoroutineScope = screenModelScope,
        context: CoroutineContext = defaultDispatcher,
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
            } catch (_: kotlin.coroutines.cancellation.CancellationException) {
            } catch (_: CancellationException) {
            } catch (throwable: Throwable) {
                logger.log("Error: ${throwable.message}")

                // Ignore if it's a cancelled job
                if (throwable.message.equals("Job was cancelled", true)) {
                    return@launch
                }

                // Handle auth errors
                if (isAuthError(throwable)) {
                    handleAuthError()
                    return@launch
                }

                // Handle network errors
                if (isNetworkError(throwable)) {
                    handleNetworkError()
                    return@launch
                }

                // Handle other errors
                val errorMessage = when (throwable) {
                    is AppException -> {
                        throwable.getErrorMessage().orEmpty()
                    }

                    is SocketTimeoutException,
                    is HttpRequestTimeoutException,
                    is ConnectTimeoutException -> {
                        resourceProvider.getString(MR.strings.server_taking_too_long)
                    }

                    else -> {
                        throwable.message.orEmpty()
                    }
                }
                if (hasLoading) hideLoading()
                showError(
                    error = errorMessage,
                    errorType = errorType
                )
                onError?.invoke(throwable, errorMessage)
            } finally {
                onComplete?.invoke()
            }
        }

        // Invoke on created and return the new job
        onCreated.invoke(newJob)
        return newJob
    }

    open fun <T> executeSilent(
        block: suspend () -> T,
        scope: CoroutineScope = screenModelScope,
        context: CoroutineContext = defaultDispatcher,
        debounce: Long = 0,
        oldDebounceJob: Job? = null,
        onCreated: (Job) -> Unit = {},
        onError: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ): Job {
        // Cancel old job if required
        if (debounce != 0L) oldDebounceJob?.cancel()

        // Create the new job
        val newJob = scope.launch(context) {
            // Debounce if required
            if (debounce != 0L) delay(debounce)

            try {
                block.invoke()
            } catch (_: kotlin.coroutines.cancellation.CancellationException) {
            } catch (_: CancellationException) {
            } catch (throwable: Throwable) {
                logger.log("Error: ${throwable.message}")

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
        return throwable is AppException && throwable.getHttpErrorCode() == 401
    }

    private fun isNetworkError(throwable: Throwable): Boolean {
        return throwable is AppException && throwable.getErrorCode() == NetworkMapperConstants.NO_INTERNET_CONNECTION
    }

    open suspend fun logout() {}

    open fun handleAuthError() {
        coreGlobalState.confirmationPopup(
            ConfirmationPopupParams(
                title = resourceProvider.getString(MR.strings.session_expired),
                body = resourceProvider.getString(MR.strings.your_session_expired_login_again),
                positiveButtonText = resourceProvider.getString(MR.strings.ok),
                isCancellable = false,
                onPositiveClick = {
                    executeSilent({
                        logout()
                    })
                },
                onNegativeClick = {
                    executeSilent({
                        logout()
                    })
                }
            )
        )
    }


    private fun handleNetworkError() {
        hideLoading()
        coreGlobalState.snackBar(
            SnackBarParams(
                message = resourceProvider.getString(MR.strings.no_internet_connection_check_connection),
                type = SnackBarType.ERROR
            )
        )
    }

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

    protected fun showError(
        errorRes: StringResource,
        errorType: ErrorType = defaultErrorType
    ) {
        showError(
            error = resourceProvider.getString(errorRes),
            errorType = errorType
        )
    }

    protected fun showError(
        error: String,
        errorType: ErrorType = defaultErrorType
    ) {
        when (errorType) {
            ErrorType.Popup -> coreGlobalState.messagePopup(
                MessagePopupParams(
                    title = resourceProvider.getString(MR.strings.error),
                    body = error
                )
            )

            ErrorType.SnackBar -> coreGlobalState.snackBar(
                SnackBarParams(
                    message = error,
                    type = SnackBarType.ERROR
                )
            )

            ErrorType.NoError -> {}
        }
    }

    protected inline fun <reified D, reified R> NavManager.collectNavResult(
        crossinline onResult: (R) -> Unit
    ) {
        executeSilent({
            // Get nav manager
            val navManager = this@collectNavResult

            // Collect results with view model scope
            navManager.onNavResult<D, R>(callback = onResult)
        })
    }

    suspend fun checkAppUpdates(
        appUpdateSource: AppUpdateSource,
        showTitle: Boolean = true,
        title: String? = null,
        image: CommonImageResource? = null,
        onUpdateClick: (() -> Unit)? = null,
        onSkipUpdateClick: (() -> Unit)? = null,
        onProceedAction: () -> Unit
    ) {
        // check if the title is enabled and handle the title
        val forceUpdateTitle = if (showTitle) title
            ?: resourceProvider.getString(MR.strings.force_update_title) else null

        // check for app updates first
        val response = forceUpdateRepository.checkForceUpdate(appUpdateSource = appUpdateSource)

        if (response != null) {
            coreGlobalState.forceUpdatePopup(
                params = ForceUpdatePopupParams(
                    isRequired = response.isRequired,
                    title = forceUpdateTitle,
                    body = response.message,
                    image = image ?: MR.images.ic_upgrade.asCommon(),
                    updateButtonText = resourceProvider.getString(MR.strings.update_button),
                    skipUpdateButtonText = resourceProvider.getString(MR.strings.skip_update_button),
                    onDismiss = {
                        if (response.isRequired.not()) {
                            onProceedAction.invoke()
                        }
                    },
                    onUpdateClick = {
                        if (onUpdateClick != null) {
                            onUpdateClick.invoke()
                        } else {
                            intentLauncher.launchStore(response.iosAppStoreId)
                        }
                    },
                    onSkipUpdateClicked = {
                        onSkipUpdateClick?.invoke()
                    }
                )
            )
        } else {
            onProceedAction()
        }
    }

    protected fun nativeItemPicker(
        items: List<PickerItem>,
        selectedItem: PickerItem? = null,
        onItemSelected: (PickerItem) -> Unit
    ) {
        if (getPlatformType() == PlatformType.IOS) {
            // Pick using native item picker
            pickItemUsingNativePicker(
                items = items,
                selectedItem = selectedItem,
                onItemSelected = onItemSelected
            )
        } else {
            // Pick using item picker sheet
            pickItemUsingPickerSheet(
                items = items,
                selectedItem = selectedItem,
                onItemSelected = onItemSelected
            )
        }
    }

    private fun pickItemUsingNativePicker(
        items: List<PickerItem>,
        selectedItem: PickerItem? = null,
        onItemSelected: (PickerItem) -> Unit
    ) {
        coreGlobalState.itemPicker(
            ItemPickerParams(
                items = items,
                selectedItem = selectedItem,
                onItemSelected = onItemSelected
            )
        )
    }

    private fun pickItemUsingPickerSheet(
        items: List<PickerItem>,
        selectedItem: PickerItem? = null,
        onItemSelected: (PickerItem) -> Unit
    ) {
        // Observe item picker results
        onPickerItemSelected = onItemSelected
        observePickerItemResultsIfRequired()

        // Open item picker sheet
        navManager.navigateToBottomSheet(
            ItemPickerSheet(
                items = items,
                selectedItem = selectedItem
            )
        )
    }

    private fun observePickerItemResultsIfRequired() {
        // Validate, if we should observe
        // Should observe one time only to avoid issues
        if (isObservingItemPicker) return

        // Observe
        navManager.collectNavResult<ItemPickerSheet, PickerItem> {
            onPickerItemSelected?.invoke(it)
            onPickerItemSelected = null
        }

        // Update the flag
        isObservingItemPicker = true
    }

    override fun onDispose() {
        screenModelScope.cancel()
        super.onDispose()
    }

    open val defaultLoadingType: LoadingType = LoadingType.LottieBlocking()

    open val defaultErrorType: ErrorType = ErrorType.Popup

    open val defaultDispatcher: CoroutineContext = Dispatchers.IO
}

private fun extractErrorCodeAndMessage(jsonString: String): Pair<String, Int> {
    if (jsonString.trim().isEmpty()) return "" to -1
    try {
        // Parse the JSON string safely
        val jsonObject = JsonWithIgnoredUnknownKeys.parseToJsonElement(jsonString).let {
            // Ensure the parsed element is a JsonObject
            it as? JsonObject ?: return jsonString to -1
        }

        // Extract the message and code, with default values if not found or in the wrong format
        val message = jsonObject["message"]?.jsonPrimitive?.content.orEmpty()
        val code = jsonObject["code"]?.jsonPrimitive?.content?.toIntOrNull() ?: 0

        return message to code
    } catch (throwable: Throwable) {
        // Handle parsing errors gracefully
        return jsonString to -1
    }
}

expect fun AppException.getHttpErrorCode(): Int?
expect fun AppException.getErrorCode(): Int?
expect fun AppException.getErrorMessage(): String?