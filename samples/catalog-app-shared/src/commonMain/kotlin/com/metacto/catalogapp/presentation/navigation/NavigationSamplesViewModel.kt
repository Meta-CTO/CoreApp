package com.metacto.catalogapp.presentation.navigation

import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.navigation.NavigationSamplesContract.Effect
import com.metacto.catalogapp.presentation.navigation.NavigationSamplesContract.Event
import com.metacto.catalogapp.presentation.navigation.NavigationSamplesContract.State
import com.metacto.core.ui.navigation.NavManager
import com.metacto.core.ui.navigation.NavigateBehaviour
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch

internal class NavigationSamplesViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.NavigateToFirstSample -> navigateToFirstSample()
        Event.NavigateToSecondSample -> navigateToSecondSample()
        Event.NavigateToThirdSample -> navigateToThirdSample()
        Event.NavigateAndPopCurrent -> navigateAndPopCurrent()
        Event.ClearAndNavigate -> clearAndNavigate()
        Event.PopToFirst -> popToFirst()
        Event.PopByTwo -> popByTwo()
        Event.GetLastScreen -> getLastScreen()
        Event.CheckCurrentScreen -> checkCurrentScreen()
        Event.GoBack -> goBack()
        Event.NavigateWithResult -> navigateWithResult()
        Event.GoBackWithResult -> goBackWithResult()
    }

    private fun init() {
        setState {
            copy(isInitialized = true)
        }
        updateHistory("Navigation samples initialized")
        
        // Setup result listener
        screenModelScope.launch {
            navManager.onNavResult<NavigationSampleDetailScreen, String> { result ->
                sendEffect(Effect.ReceivedResult(result))
                updateHistory("Received result: $result")
            }
        }
    }

    private fun navigateToFirstSample() {
        navManager.navigate(
            destination = NavigationSampleDetailScreen(
                title = "First Sample Screen",
                description = "This is the first navigation sample screen"
            )
        )
        updateHistory("Navigated to First Sample")
        sendEffect(Effect.NavigationCompleted)
    }

    private fun navigateToSecondSample() {
        navManager.navigate(
            destination = NavigationSampleDetailScreen(
                title = "Second Sample Screen",
                description = "This is the second navigation sample screen"
            ),
            behaviour = NavigateBehaviour.SingleTop
        )
        updateHistory("Navigated to Second Sample (SingleTop)")
        sendEffect(Effect.NavigationCompleted)
    }

    private fun navigateToThirdSample() {
        navManager.navigate(
            destination = NavigationSampleDetailScreen(
                title = "Third Sample Screen",
                description = "This is the third navigation sample screen"
            )
        )
        updateHistory("Navigated to Third Sample")
        sendEffect(Effect.NavigationCompleted)
    }

    private fun navigateAndPopCurrent() {
        navManager.navigateAndPopupCurrent(
            destination = NavigationSampleDetailScreen(
                title = "Replaced Screen",
                description = "This screen replaced the previous one"
            )
        )
        updateHistory("Navigate and pop current")
        sendEffect(Effect.NavigationCompleted)
    }

    private fun clearAndNavigate() {
        navManager.clearAndNavigate(
            destination = NavigationSampleDetailScreen(
                title = "New Root Screen",
                description = "Cleared stack and navigated here"
            )
        )
        updateHistory("Clear and navigate to new root")
        sendEffect(Effect.NavigationCompleted)
    }

    private fun popToFirst() {
        navManager.popToInclusive(NavigationSamplesScreen::class)
        updateHistory("Popped to NavigationSamplesScreen")
        sendEffect(Effect.NavigationCompleted)
    }

    private fun popByTwo() {
        navManager.popByCount(2)
        updateHistory("Popped 2 screens from stack")
        sendEffect(Effect.NavigationCompleted)
    }

    private fun getLastScreen() {
        screenModelScope.launch {
            val lastScreen = navManager.getLastScreen()
            val screenInfo = lastScreen?.let {
                "Last screen: ${it::class.simpleName}"
            } ?: "No screen in stack"
            
            setState {
                copy(lastScreen = lastScreen, currentScreenInfo = screenInfo)
            }
            sendEffect(Effect.ScreenCheckCompleted(screenInfo))
            updateHistory("Got last screen: $screenInfo")
        }
    }

    private fun checkCurrentScreen() {
        screenModelScope.launch {
            val hasNavigationScreen = navManager.checkScreenByClass(NavigationSamplesScreen::class)
            val hasDetailScreen = navManager.checkScreenByClass(NavigationSampleDetailScreen::class)
            
            val info = buildString {
                appendLine("Has NavigationSamplesScreen: $hasNavigationScreen")
                appendLine("Has NavigationSampleDetailScreen: $hasDetailScreen")
            }
            
            setState {
                copy(hasScreenResult = hasNavigationScreen, currentScreenInfo = info)
            }
            sendEffect(Effect.ScreenCheckCompleted(info))
            updateHistory("Checked screens in stack")
        }
    }

    private fun goBack() {
        navManager.goBack()
        updateHistory("Go back triggered")
        sendEffect(Effect.NavigationCompleted)
    }

    private fun navigateWithResult() {
        navManager.navigate(
            destination = NavigationSampleDetailScreen(
                title = "Screen with Result",
                description = "This screen can send back a result",
                canSendResult = true
            )
        )
        updateHistory("Navigated to screen that can send result")
        sendEffect(Effect.NavigationCompleted)
    }

    private fun goBackWithResult() {
        navManager.goBackWithResult(
            source = NavigationSamplesScreen::class.simpleName,
            result = "Sample result from navigation screen"
        )
        updateHistory("Go back with result")
        sendEffect(Effect.NavigationCompleted)
    }

    private fun updateHistory(action: String) {
        setState {
            copy(
                navigationHistory = navigationHistory + action
            )
        }
    }
}