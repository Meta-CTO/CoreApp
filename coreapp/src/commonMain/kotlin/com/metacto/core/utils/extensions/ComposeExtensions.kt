package com.metacto.core.utils.extensions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.ViewSideEffect
import dev.icerock.moko.resources.AssetResource
import dev.icerock.moko.resources.compose.readTextAsState
import io.github.alexzhirkevich.compottie.LottieCompositionResult
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.michaelrocks.libphonenumber.kotlin.MetadataLoader
import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private var screenSize: Pair<Int, Int> = 0 to 0

fun getScreenSize(): Pair<Int, Int> {
    return screenSize
}

fun setScreenSize(width: Int, height: Int) {
    screenSize = width to height
}

@Composable
fun Dp.toPx(): Float {
    return LocalDensity.current.run { this@toPx.toPx() }
}

@Composable
fun Int.toDp(): Dp {
    return LocalDensity.current.run { this@toDp.toDp() }
}

@Composable
fun Double.toDp(): Dp {
    return LocalDensity.current.run {
        (this@toDp / density).dp
    }
}

@Composable
fun Float.toDp(): Dp {
    return this.toDouble().toDp()
}

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: (() -> Unit)?
): Modifier = composed {
    clickable(
        indication = null,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick?.invoke()
    }
}

inline fun Modifier.suspendNoRippleClickable(
    enabled: Boolean = true,
    crossinline onClick: suspend CoroutineScope.() -> Unit
): Modifier = composed {
    val coroutineScope = rememberCoroutineScope()
    clickable(
        indication = null,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        coroutineScope.launch { onClick.invoke(this) }
    }
}

fun Modifier.clipIfNotNull(shape: Shape?): Modifier {
    return if (shape != null) this.clip(shape) else this
}

fun Modifier.shadowIfNotNull(elevation: Dp, shape: Shape?): Modifier {
    return if (shape != null) this.shadow(elevation, shape) else this
}

fun Modifier.scaleIf(condition: Boolean, scaleX: Float, scaleY: Float): Modifier {
    return if (condition) this.scale(scaleX, scaleY) else this
}

fun tintIfNotNull(color: Color?): ColorFilter? {
    return if (color != null) ColorFilter.tint(color) else null
}

fun Modifier.borderIfNotNull(border: BorderStroke?, shape: Shape?): Modifier {
    return if (border != null) this.border(border, shape ?: RectangleShape) else this
}

fun Modifier.backgroundIfNotNull(color: Color?): Modifier {
    return if (color != null) background(color) else this
}

@Composable
fun animateAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment> {
    val bias by animateFloatAsState(targetBiasValue)
    return derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }
}

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.scrollToNextPage() {
    try {
        animateScrollToPage(currentPage + 1)
    } catch (_: Throwable) {
    }
}

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.scrollToPreviousPage() {
    try {
        animateScrollToPage(currentPage - 1)
    } catch (_: Throwable) {
    }
}

fun Dp.half() = this.div(2)

fun Dp.negative() = this.times(-1)

@Composable
fun animateTextStyleAsState(
    targetValue: TextStyle,
    animationSpec: AnimationSpec<Float> = spring(),
    finishedListener: ((TextStyle) -> Unit)? = null
): State<TextStyle> {

    val animation = remember { Animatable(0f) }
    var previousTextStyle by remember { mutableStateOf(targetValue) }
    var nextTextStyle by remember { mutableStateOf(targetValue) }

    val textStyleState = remember(animation.value) {
        derivedStateOf {
            lerp(previousTextStyle, nextTextStyle, animation.value)
        }
    }

    LaunchedEffect(targetValue, animationSpec) {
        previousTextStyle = textStyleState.value
        nextTextStyle = targetValue
        animation.snapTo(0f)
        animation.animateTo(1f, animationSpec)
        finishedListener?.invoke(textStyleState.value)
    }

    return textStyleState
}

fun <T> LazyListScope.itemIfNotNull(data: T?, content: @Composable (T) -> Unit) {
    if (data != null) item {
        content(data)
    }
}

@Composable
fun rememberCurrentOffset(state: LazyListState): State<Int> {
    val position = remember { derivedStateOf { state.firstVisibleItemIndex } }
    val itemOffset = remember { derivedStateOf { state.firstVisibleItemScrollOffset } }
    val lastPosition = rememberPrevious(position.value)
    val lastItemOffset = rememberPrevious(itemOffset.value)
    val currentOffset = remember { mutableStateOf(0) }

    LaunchedEffect(position.value, itemOffset.value) {
        if (lastPosition == null || position.value == 0) {
            currentOffset.value = itemOffset.value
        } else if (lastPosition == position.value) {
            currentOffset.value += (itemOffset.value - (lastItemOffset ?: 0))
        } else if (lastPosition > position.value) {
            currentOffset.value -= (lastItemOffset ?: 0)
        } else { // lastPosition.value < position.value
            currentOffset.value += itemOffset.value
        }
    }

    return currentOffset
}

@Composable
fun <T> rememberRef(): MutableState<T?> {
    // for some reason it always recreated the value with vararg keys,
    // leaving out the keys as a parameter for remember for now
    return remember() {
        object : MutableState<T?> {
            override var value: T? = null

            override fun component1(): T? = value

            override fun component2(): (T?) -> Unit = { value = it }
        }
    }
}

@Composable
fun <T> rememberPrevious(
    current: T,
    shouldUpdate: (prev: T?, curr: T) -> Boolean = { a: T?, b: T -> a != b },
): T? {
    val ref = rememberRef<T>()

    SideEffect {
        if (shouldUpdate(ref.value, current)) {
            ref.value = current
        }
    }

    return ref.value
}

@Composable
fun FontFamily(vararg fonts: Font?): FontFamily? {
    val filteredFonts = fonts.filterNotNull()
    return if (filteredFonts.isEmpty()) null else FontFamily(filteredFonts)
}

fun Modifier.heightIfNotNull(height: Dp?): Modifier {
    return if (height != null) this.height(height) else this
}

@Composable
fun isKeyboardVisible(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

@Composable
fun ScrollState.onScrolling(callback: () -> Unit) {
    LaunchedEffect(this) {
        snapshotFlow { isScrollInProgress }
            .collect {
                if (it) callback.invoke()
            }
    }
}

@Composable
fun LazyListState.onScroll(
    onScrollUp: (() -> Unit)? = null,
    onScrollDown: (() -> Unit)? = null,
    onScroll: (() -> Unit)? = null,
) {
    // State to store the previous scroll position
    var previousIndex by remember { mutableStateOf(0) }
    var previousScrollOffset by remember { mutableStateOf(0) }

    LaunchedEffect(this) {
        snapshotFlow { firstVisibleItemIndex to firstVisibleItemScrollOffset }
            .distinctUntilChanged()
            .collect { (index, scrollOffset) ->
                if (index != previousIndex || scrollOffset != previousScrollOffset) {
                    if (index > previousIndex || (index == previousIndex && scrollOffset > previousScrollOffset)) {
                        onScrollDown?.invoke()
                    } else {
                        onScrollUp?.invoke()
                    }
                    onScroll?.invoke()
                    previousIndex = index
                    previousScrollOffset = scrollOffset
                }
            }
    }
}

@Composable
fun <T : ViewSideEffect> Flow<T>.consume(
    key: String = SIDE_EFFECTS_KEY,
    action: (effect: T) -> Unit
) {
    LaunchedEffect(key) { onEach(action).collect() }
}

fun ULong?.toColor(): Color? {
    return if (this != null) Color(this) else null
}

fun Modifier.focusRequesterIfNotNull(focusRequester: FocusRequester?): Modifier {
    return if (focusRequester != null) this.focusRequester(focusRequester) else this
}

@Composable
fun rememberFocusRequester(): FocusRequester {
    return remember { FocusRequester() }
}

@Composable
fun rememberIOCoroutineScope(): CoroutineScope {
    return rememberCoroutineScope {
        Dispatchers.IO
    }
}

@Composable
fun DelayedLaunchedEffect(key: Any? = null, delay: Long, callback: suspend () -> Unit) {
    val coroutineScope = rememberIOCoroutineScope()
    LaunchedEffect(key) {
        coroutineScope.launch {
            delay(delay)
            withContext(Dispatchers.Main) {
                callback.invoke()
            }
        }
    }
}

@Composable
fun IOLaunchedEffect(key: Any? = null, callback: suspend () -> Unit) {
    val coroutineScope = rememberIOCoroutineScope()
    LaunchedEffect(key) {
        coroutineScope.launch {
            callback.invoke()
        }
    }
}

@Composable
fun rememberPhoneNumberUtil(): PhoneNumberUtil {
    val metadataLoader = defaultMetadataLoader()
    return remember {
        PhoneNumberUtil.Companion.createInstance(
            metadataLoader = metadataLoader
        )
    }
}

@Composable
fun rememberLottieComposition(res: AssetResource): LottieCompositionResult {
    // Load json if required
    var json by remember(res) { mutableStateOf<String?>(null) }
    if (json == null) json = res.readTextAsState().value

    // Then return the composition
    return io.github.alexzhirkevich.compottie.rememberLottieComposition(
        LottieCompositionSpec.JsonString(
            json.orEmpty()
        )
    )
}

fun Modifier.borderIf(
    condition: Boolean,
    border: BorderStroke,
    shape: Shape = RectangleShape
): Modifier {
    return if (condition) this.border(border = border, shape = shape) else this
}

@Composable
fun Modifier.verticalScrollIf(
    condition: Boolean,
    state: ScrollState? = rememberScrollState()
): Modifier {
    return if (condition && state != null) this.verticalScroll(state) else this
}

@Composable
fun rememberScrollStateIf(condition: Boolean): ScrollState? {
    return if (condition) rememberScrollState() else null
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberPullRefreshStateIf(
    condition: Boolean,
    refreshing: Boolean,
    onRefresh: () -> Unit,
): PullRefreshState? {
    return if (condition) return rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    ) else {
        null
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Modifier.pullRefreshIf(
    condition: Boolean,
    state: PullRefreshState?
): Modifier {
    return if (condition && state != null) this.pullRefresh(state) else this
}

fun Color.isDark(): Boolean {
    val darkness = 1 - (0.299 * red + 0.587 * green + 0.114 * blue)
    return darkness >= 0.5
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

@Composable
expect fun defaultMetadataLoader(): MetadataLoader

@Composable
expect fun openUrlInBrowser(url: String)

@Composable
expect fun getNotchHeight(): Dp

@Composable
expect fun getBottomHandleHeight(): Dp

@Composable
expect fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap?

@Composable
expect fun isGesturesNavBarEnabled(): Boolean

@Composable
expect fun setStatusBarColor(isDark: Boolean)

@Composable
expect fun setNavigationBarColor(isDark: Boolean)

@Composable
expect fun dismissKeyboard()