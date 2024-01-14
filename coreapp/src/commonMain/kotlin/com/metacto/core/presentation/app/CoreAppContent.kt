@file:OptIn(ExperimentalComposeUiApi::class)

package com.metacto.core.presentation.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import cafe.adriel.voyager.core.screen.Screen
import com.metacto.core.navigation.CoreAppNavigator
import com.metacto.core.navigation.NavManager
import com.metacto.core.presentation.components.dialogs.ChoicesDialog
import com.metacto.core.presentation.components.dialogs.ConfirmationDialog
import com.metacto.core.presentation.components.dialogs.MessageDialog
import com.metacto.core.presentation.components.dialogs.SuccessDialog
import com.metacto.core.presentation.components.dialogs.datePicker.DatePickerDialog
import com.metacto.core.presentation.components.dialogs.timePicker.TimePickerDialog
import com.metacto.core.presentation.components.progress.LottieProgressIndicator
import com.metacto.core.presentation.components.progress.PrimaryProgressIndicator
import com.metacto.core.presentation.components.progress.ProgressIndicator
import com.metacto.core.presentation.components.progress.SecondaryProgressIndicator
import com.metacto.core.presentation.components.snackBar.ErrorSnackBar
import com.metacto.core.presentation.components.snackBar.SuccessSnackBar
import com.metacto.core.presentation.components.visibilities.TopSlideVisibility
import com.metacto.core.presentation.globalState.ICoreGlobalState
import com.metacto.core.presentation.globalState.models.LoadingType
import com.metacto.core.presentation.globalState.models.SnackBarType
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.setNavigationBarColor
import com.metacto.core.utils.extensions.setStatusBarColor
import org.koin.compose.rememberKoinInject

@Composable
fun CoreAppContent(
    modifier: Modifier = Modifier,
    globalState: ICoreGlobalState = rememberKoinInject(),
    navManager: NavManager = rememberKoinInject(),
    startScreen: Screen,
) {
    // Get global states
    val messageParams by globalState.messagePopupState
    val successParams by globalState.successPopupState
    val confirmationParams by globalState.confirmationPopupState
    val choicesParams by globalState.choicesPopupState
    val datePickerParams by globalState.datePickerState
    val timePickerParams by globalState.timePickerState
    val loadingType by globalState.loadingState
    val snackBarParams by globalState.snackBarState
    val isStatusBarDark by globalState.isStatusBarDarkState
    val isNavigationBarDark by globalState.isNavigationBarDarkState

    // Render container box
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Render navigator
        CoreAppNavigator(
            modifier = Modifier.fillMaxSize(),
            navManager = navManager,
            startScreen = startScreen
        )

        // Handle message popup
        messageParams?.let { params ->
            MessageDialog(
                isCancellable = params.isCancellable,
                title = params.title,
                body = params.body,
                buttonText = params.buttonText,
                onPositiveClick = {
                    globalState.idle()
                    params.onPositiveClick?.invoke()
                },
                onDismiss = {
                    globalState.idle()
                    params.onDismiss?.invoke()
                }
            )
        }

        // Handle success popup
        successParams?.let { params ->
            SuccessDialog(
                isCancellable = params.isCancellable,
                title = params.title,
                body = params.body,
                buttonText = params.buttonText,
                onPositiveClick = {
                    globalState.idle()
                    params.onPositiveClick?.invoke()
                },
                onDismiss = {
                    globalState.idle()
                    params.onDismiss?.invoke()
                }
            )
        }

        // Handle confirmation popup
        confirmationParams?.let { params ->
            ConfirmationDialog(
                isCancellable = params.isCancellable,
                title = params.title,
                body = params.body,
                positiveButtonText = params.positiveButtonText,
                negativeButtonText = params.negativeButtonText,
                onPositiveClick = {
                    globalState.idle()
                    params.onPositiveClick?.invoke()
                },
                onNegativeClick = {
                    globalState.idle()
                    params.onNegativeClick?.invoke()
                },
                onDismiss = {
                    globalState.idle()
                    params.onDismiss?.invoke()
                }
            )
        }

        // Handle choices popup
        choicesParams?.let { params ->
            ChoicesDialog(
                isCancellable = params.isCancellable,
                title = params.title,
                choices = params.choices,
                onChoiceSelected = { choice, index ->
                    params.onChoiceSelected?.invoke(choice, index)
                    globalState.idle()
                },
                onDismiss = {
                    globalState.idle()
                    params.onDismiss?.invoke()
                }
            )
        }

        // Handle date picker popup
        datePickerParams?.let { params ->
            DatePickerDialog(
                selectedDate = params.selectedDate,
                minDate = params.minDate,
                maxDate = params.maxDate,
                isCancellable = params.isCancellable,
                onDatePicked = {
                    globalState.idle()
                    params.onDatePicked.invoke(it)
                },
                onDismiss = {
                    globalState.idle()
                }
            )
        }

        // Handle time picker popup
        timePickerParams?.let { params ->
            TimePickerDialog(
                selectedTime = params.selectedTime,
                minTime = params.minTime,
                maxTime = params.maxTime,
                isCancellable = params.isCancellable,
                onTimePicked = {
                    globalState.idle()
                    params.onTimePicked.invoke(it)
                },
                onDismiss = {
                    globalState.idle()
                }
            )
        }

        // Handle progress indicator
        when (loadingType) {
            LoadingType.PRIMARY_CIRCULAR -> PrimaryProgressIndicator(
                isBlocking = false
            )

            LoadingType.PRIMARY_CIRCULAR_BLOCKING -> PrimaryProgressIndicator(
                isBlocking = true
            )

            LoadingType.SECONDARY_CIRCULAR -> SecondaryProgressIndicator(
                isBlocking = false
            )

            LoadingType.SECONDARY_CIRCULAR_BLOCKING -> SecondaryProgressIndicator(
                isBlocking = true
            )

            LoadingType.LOTTIE -> LottieProgressIndicator(
                isBlocking = false
            )

            LoadingType.LOTTIE_BLOCKING -> LottieProgressIndicator(
                isBlocking = true
            )

            LoadingType.HIDDEN_BLOCKING -> ProgressIndicator(
                color = CoreTheme.colors.transparent,
                isBlocking = true
            )

            LoadingType.NO_LOADING -> {}
        }
    }

    // Handle snack bar
    TopSlideVisibility(
        visible = snackBarParams.isVisible
    ) {
        when (snackBarParams.type) {
            SnackBarType.ERROR -> ErrorSnackBar(
                modifier = Modifier.padding(top = CoreTheme.spacings.noSpacing),
                text = snackBarParams.message,
                onClick = { globalState.hideSnackBar() }
            )

            SnackBarType.SUCCESS -> SuccessSnackBar(
                text = snackBarParams.message,
                onClick = { globalState.hideSnackBar() }
            )
        }
    }

    // Handle dismiss keyboard state
    if (globalState.dismissKeyboardState.value) {
        globalState.resetDismissKeyboardState()
        LocalSoftwareKeyboardController.current?.hide()
    }

    // Handle changing status bar color
    setStatusBarColor(
        isDark = isStatusBarDark
    )

    // Handle changing navigation bar color
    setNavigationBarColor(
        isDark = isNavigationBarDark
    )
}
