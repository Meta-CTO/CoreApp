package com.metacto.catalogapp.crash

import com.metacto.core.extensions.randomUUID
import com.metacto.core.files.IFileManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CrashLogger : KoinComponent {
    private val crashlytics by inject<FirebaseCrashlytics>()
    private val fileManager by inject<IFileManager>()
    private var currentScreenState: String? = null

    init {
        setupCrashHandler {
            saveCurrentScreenState()
        }
    }

    fun setCurrentScreen(screenName: String) {
        crashlytics.setCustomKey("CurrentScreen", screenName)
    }

    fun setCurrentScreenState(state: Any) {
        currentScreenState = state.toString()
    }

    fun logEvent(eventName: String, parameters: Map<String, Any> = emptyMap()) {
        // Filter out text field changes to avoid cluttering logs
        if (eventName.endsWith("Changed")) return

        if (parameters.isEmpty()) {
            crashlytics.log(eventName)
        } else {
            crashlytics.log("$eventName: $parameters")
        }
    }

    fun logException(exception: Throwable) {
        crashlytics.recordException(exception)
    }

    fun setUserId(userId: String) {
        crashlytics.setUserId(userId)
    }

    fun setCustomKey(key: String, value: String) {
        crashlytics.setCustomKey(key, value)
    }

    fun saveCurrentScreenState() {
        try {
            val filePath = fileManager.createFile(
                fileName = "crash_state_${randomUUID()}.txt",
                content = currentScreenState?.formatState().orEmpty()
            )

            crashlytics.setCustomKey("StateFile", filePath)
            crashlytics.log("Final state saved before crash: $filePath")
        } catch (e: Throwable) {
            crashlytics.setCustomKey(
                "StateError",
                "Failed to save state before crash: ${e.message}"
            )
        }
    }

    fun clear() {
        crashlytics.setUserId("")
    }

    private fun String.formatState(): String {
        val state = this
        val result = StringBuilder()
        var indentLevel = 0
        var i = 0

        while (i < state.length) {
            val char = state[i]

            when (char) {
                '(' -> {
                    result.append(char)
                    if (i + 1 < state.length && state[i + 1] != ')') {
                        result.append('\n')
                        indentLevel++
                        result.append("  ".repeat(indentLevel))
                    }
                }

                ')' -> {
                    if (result.isNotEmpty() && result.last() != '(' && result.last() != '\n') {
                        result.append('\n')
                        indentLevel = maxOf(0, indentLevel - 1)
                        result.append("  ".repeat(indentLevel))
                    } else {
                        indentLevel = maxOf(0, indentLevel - 1)
                    }
                    result.append(char)
                }

                ',' -> {
                    result.append(char)
                    if (i + 1 < state.length && state[i + 1] == ' ') {
                        result.append('\n')
                        result.append("  ".repeat(indentLevel))
                        i++ // Skip the space after comma
                    }
                }

                '=' -> {
                    result.append(" = ")
                }

                else -> {
                    result.append(char)
                }
            }
            i++
        }

        return result.toString()
    }
}

internal expect fun setupCrashHandler(onCrash: () -> Unit)