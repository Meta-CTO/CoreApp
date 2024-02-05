package com.sampleApp.app.domain.events

import com.sampleApp.app.domain.TestUserModel

sealed class UserEvent {
    data class UserUpdated(val userName: String) : UserEvent() {
        companion object {
            val eventName = UserUpdated::class.simpleName.orEmpty()
        }
    }

    class UserDeleted : UserEvent() {
        companion object {
            val eventName = UserDeleted::class.simpleName.orEmpty()
        }
    }

    data class UserAdded(val user: TestUserModel) : UserEvent() {
        companion object {
            val eventName = UserAdded::class.simpleName.orEmpty()
        }
    }
}
