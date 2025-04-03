package com.sampleApp.app.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import com.metacto.core.resources.IFileResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

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

    @OptIn(ExperimentalResourceApi::class)
    override fun getUri(): String {
        return Res.getUri(path)
    }
}