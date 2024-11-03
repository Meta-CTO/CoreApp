package com.metacto.core.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import com.metacto.coreApp.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

interface IFileResource {
    val path: String

    @Composable
    fun readAsState(): State<String?>
}

internal data class FileResource(
    override val path: String
) : IFileResource {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun readAsState(): State<String?> {
        return produceState<String?>(null, this) {
            value = Res.readBytes(path).decodeToString()
        }
    }
}