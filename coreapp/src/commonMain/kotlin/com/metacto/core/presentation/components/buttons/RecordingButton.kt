package com.metacto.core.presentation.components.buttons

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.theme.CoreTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@Composable
fun RecordingButton(
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onDurationChanged: (Long) -> Unit,
    maxDuration: Long? = null,
    longPressDelay: Long = 500L,
    ringSize: Dp = CoreTheme.spacings.recordingButton.ringSize,
    ringColor: Color = CoreTheme.colors.recordingButton.ringColor,
    ringWidth: Dp = CoreTheme.spacings.recordingButton.ringWidth,
    recordingButtonSize: Dp = CoreTheme.spacings.recordingButton.recordingButtonSize,
    notRecordingButtonSize: Dp = CoreTheme.spacings.recordingButton.notRecordingButtonSize,
    recordingButtonColor: Color = CoreTheme.colors.recordingButton.recordingButtonColor,
    notRecordingButtonColor: Color = CoreTheme.colors.recordingButton.notRecordingButtonColor
) {
    var isRecording by remember { mutableStateOf(false) }
    var isLongPress by remember { mutableStateOf(false) }
    var isPressHandled by remember { mutableStateOf(false) }
    var recordingDuration by remember { mutableStateOf(0L) }

    val cornerRadius by animateFloatAsState(targetValue = if (isRecording) 8f else 50f)
    val buttonSize = if (isRecording) recordingButtonSize else notRecordingButtonSize
    val buttonColor = if (isRecording) recordingButtonColor else notRecordingButtonColor

    val coroutineScope = rememberCoroutineScope()
    val progress = remember { Animatable(0f) } // Progress of the ring (0f to 1f)

    // Track the recording duration independently of the animation
    LaunchedEffect(isRecording) {
        if (isRecording) {
            val startTime = Clock.System.now().toEpochMilliseconds()
            progress.snapTo(0f) // Reset progress when recording starts

            while (isRecording) {
                val currentTime = Clock.System.now().toEpochMilliseconds()
                recordingDuration = (currentTime - startTime) / 1000 // Seconds

                // Notify the parent about duration changes
                onDurationChanged(recordingDuration)

                // Stop recording if maxDuration is reached
                if (maxDuration != null && recordingDuration >= maxDuration) {
                    isRecording = false
                    onStopRecording()
                }
                delay(1000) // Update every second
            }
        } else {
            recordingDuration = 0L // Reset when recording stops
            progress.snapTo(0f) // Reset progress when recording stops
        }
    }

    // Start the ring animation based on maxDuration
    LaunchedEffect(isRecording, maxDuration) {
        if (isRecording && maxDuration != null) {
            // Animate the ring's progress over the entire maxDuration
            progress.animateTo(
                targetValue = 1f, // End at full circle
                animationSpec = tween(
                    durationMillis = (maxDuration * 1000).toInt(), // Duration in milliseconds
                    easing = LinearEasing // Use LinearEasing for constant speed animation
                ) // Duration = maxDuration in milliseconds
            )
            isRecording = false
            onStopRecording()
        }
    }

    // Ring
    Box(contentAlignment = Alignment.Center) {
        // Circular progress ring around the button (Always visible when not recording)
        Canvas(
            modifier = Modifier.size(ringSize) // Size of the ring container
        ) {
            drawArc(
                color = ringColor,
                startAngle = -90f, // Start at the top
                sweepAngle = if (isRecording && maxDuration != null) 360 * progress.value else 360f, // Full ring when not recording, progress during recording
                useCenter = false,
                style = Stroke(width = ringWidth.toPx()) // Thickness of the ring
            )
        }

        // Button inside the ring
        Box(
            modifier = Modifier
                .size(buttonSize) // Animated size
                .background(
                    color = buttonColor,
                    shape = RoundedCornerShape(cornerRadius.dp) // Animated corner radius
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isLongPress = false
                            isPressHandled = false
                            val pressStartTime = Clock.System.now().toEpochMilliseconds()

                            coroutineScope.launch {
                                delay(longPressDelay) // 0.5-second threshold for long press
                                if (Clock.System.now()
                                        .toEpochMilliseconds() - pressStartTime >= longPressDelay && !isPressHandled
                                ) {
                                    isLongPress = true
                                    isPressHandled = true
                                    if (!isRecording) {
                                        isRecording = true
                                        onStartRecording()
                                    }
                                }
                            }

                            tryAwaitRelease()

                            if (isLongPress) {
                                isRecording = false
                                onStopRecording()
                            }
                        },
                        onTap = {
                            if (!isLongPress && !isPressHandled) {
                                isPressHandled = true
                                isRecording = !isRecording
                                if (isRecording) {
                                    onStartRecording()
                                } else {
                                    onStopRecording()
                                }
                            }
                        }
                    )
                }
        )
    }
}