package com.sampleApp.app.presentation.profile

import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.profile.ProfileContract.Effect
import com.sampleApp.app.presentation.profile.ProfileContract.Event
import com.sampleApp.app.presentation.profile.ProfileContract.State

class ProfileViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.NativeItemPicker -> {
            nativeItemPicker(
                items = (0..20).map {
                    PickerItemUIModel(
                        key = it.toString(),
                        title = "Item $it"
                    )
                },
                selectedItem = null,
                onItemSelected = {
                    println("Selected item: $it")
                }
            )
        }
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init

        // Update the flag
        setState { copy(isInitialized = true) }
    }
}
