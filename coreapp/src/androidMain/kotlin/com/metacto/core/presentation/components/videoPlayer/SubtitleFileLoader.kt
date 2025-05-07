package com.metacto.core.presentation.components.videoPlayer

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class SubtitleFileLoader(private val context: Context) {

    suspend fun loadSubtitleFile(uri: Uri): Triple<String, String, String>? = withContext(Dispatchers.IO) {
        val contentResolver = context.contentResolver

        val fileName = contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex("_display_name")
            cursor.moveToFirst()
            cursor.getString(nameIndex)
        } ?: "subtitle.vtt"

        val languageCode = detectLanguageFromFileName(fileName)

        val stringBuilder = StringBuilder()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream, "UTF-8")).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                    stringBuilder.append('\n')
                }
            }
        }

        val content = stringBuilder.toString()
        if (content.isBlank()) {
            return@withContext null
        }

        return@withContext Triple(languageCode, fileName, content)
    }

    private fun detectLanguageFromFileName(fileName: String): String {
        val patterns = listOf(
            Regex(".*\\.([a-z]{2})\\.(?:vtt|srt)$"),
            Regex(".*_([a-z]{2})\\.(?:vtt|srt)$"),
            Regex(".*-([a-z]{2})\\.(?:vtt|srt)$")
        )

        for (pattern in patterns) {
            val match = pattern.find(fileName)
            if (match != null && match.groupValues.size > 1) {
                return match.groupValues[1]
            }
        }

        return "und"
    }

    fun getMimeTypeFromFileName(fileName: String): String {
        return when {
            fileName.endsWith(".vtt", ignoreCase = true) -> "text/vtt"
            fileName.endsWith(".srt", ignoreCase = true) -> "application/x-subrip"
            fileName.endsWith(".ttml", ignoreCase = true) -> "application/ttml+xml"
            fileName.endsWith(".ssa", ignoreCase = true) -> "text/x-ssa"
            fileName.endsWith(".ass", ignoreCase = true) -> "text/x-ssa"
            else -> "text/vtt"
        }
    }
}

@Composable
fun rememberSubtitleFilePicker(
    onSubtitleFileSelected: (language: String, fileName: String, content: String) -> Unit
): () -> Unit {
    val context = androidx.compose.ui.platform.LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val subtitleLoader = SubtitleFileLoader(context)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, flags)

            coroutineScope.launch {
                val result = subtitleLoader.loadSubtitleFile(uri)
                if (result != null) {
                    val (language, fileName, content) = result
                    onSubtitleFileSelected(language, fileName, content)
                }
            }
        }
    }

    return {
        launcher.launch(arrayOf(
            "text/vtt",
            "application/x-subrip",
            "text/plain",
            "text/x-ssa"
        ))
    }
}