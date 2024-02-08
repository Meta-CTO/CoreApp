package com.sampleApp.app.presentation.landing.splash

import com.metacto.core.presentation.globalState.models.LoadingType
import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.presentation.options.OptionsSheet
import com.metacto.core.presentation.options.models.OptionUIModel
import com.metacto.core.utils.eventBroadcaster.EventBroadcaster
import com.sampleApp.app.MR
import com.sampleApp.app.domain.TestUserModel
import com.sampleApp.app.domain.events.UserEvent
import com.sampleApp.app.presentation.components.BaseViewModel
import com.sampleApp.app.presentation.landing.splash.SplashContract.Effect
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State

class SplashViewModel(
    private val eventBroadcaster: EventBroadcaster
) : BaseViewModel<State, Event, Effect>() {
    private var clickCount = 0

    fun init(isWelcome: Boolean) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        setState { copy(isWelcome = isWelcome) }
        checkUserState()
        println(
            "laaang: " + resourceProvider.getPluralString(
                MR.plurals.d_languages,
                1,
                1
            )
        )
        println(
            "laaang: " + resourceProvider.getPluralString(
                MR.plurals.d_languages,
                3,
                3
            )
        )

        // Observe item picker results
        observeItemPickerResults()

        if (isWelcome.not()) {
            executeSilent({
                eventBroadcaster.subscribeToEvent<UserEvent.UserDeleted>(UserEvent.UserDeleted.eventName) {
                    println("user deleted: $it")
                }
            })

            executeSilent({
                eventBroadcaster.subscribeToEvent<UserEvent.UserUpdated>(UserEvent.UserUpdated.eventName) {
                    println("user updated: ${it.userName}")
                }
            })

            executeSilent({
                eventBroadcaster.subscribeToEvent<UserEvent.UserAdded>(UserEvent.UserAdded.eventName) {
                    println("user added: ${it.user}")
                }
            })
        }

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun observeItemPickerResults() {
        navManager.collectNavResult<ItemPickerSheet, PickerItem> {
            println("Item selected: $it")
            setState { copy(selectedItem = it) }
        }
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.ScreenAppeared -> {
        }

        Event.ScreenDisposed -> {
        }

        Event.TextClicked -> {
            val options = listOf(
                OptionUIModel(
                    title = "Option 1"
                ),
                OptionUIModel(
                    title = "Option 2"
                ),
                OptionUIModel(
                    title = "Option 3"
                ),
                OptionUIModel(
                    title = "Option 4"
                ),
            )
            navManager.navigateToBottomSheet(
                OptionsSheet(
                    options = options
                )
            )

//            when {
//                clickCount == 0 -> {
//                    eventBroadcaster.notify(UserEvent.UserDeleted())
//                }
//
//                clickCount == 1 -> {
//                    eventBroadcaster.notify(UserEvent.UserUpdated("shamy updated"))
//                }
//
//                clickCount == 2 -> {
//                    eventBroadcaster.notify(UserEvent.UserAdded(TestUserModel("naaame", 3, true)))
//                }
//
//                else -> {
//                    if (currentState.isWelcome) {
//                        navManager.clearAndNavigate(SplashScreen(isWelcome = true))
//                    } else {
//                        navManager.navigate(SplashScreen(isWelcome = true))
//                    }
//                }
//            }
//
//            clickCount++

//            navManager.navigate(
//                SplashScreen(
//                    isWelcome = currentState.isWelcome.not()
//                )
//            )

//            navManager.navigateToBottomSheet(
//                ItemPickerSheet(
//                    items = currentState.options,
//                    selectedItem = currentState.selectedItem
//                )
//            )
        }

        Event.AnimClicked -> {
            showLoading(LoadingType.LottieBlocking())
        }
    }

    private fun checkUserState() {
    }
}