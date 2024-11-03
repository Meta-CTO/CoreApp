package com.metacto.core.presentation.components.refreshLayout

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.metacto.core.presentation.components.refreshLayout.util.ComposePosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.abs

@Stable
class RefreshLayoutState(
    internal val onRefreshListener: RefreshLayoutState.() -> Unit
) {
    internal val refreshContentState = mutableStateOf(RefreshContentStateEnum.Stop)

    internal val refreshContentOffsetState = Animatable(0f)

    internal val composePositionState = mutableStateOf(ComposePosition.Top)

    internal val refreshContentThresholdState = mutableStateOf(0f)

    internal lateinit var coroutineScope: CoroutineScope

    var canCallRefreshListener = true

    fun getRefreshContentState(): State<RefreshContentStateEnum> = refreshContentState

    fun createRefreshContentOffsetFlow(): Flow<Float> =
        snapshotFlow { refreshContentOffsetState.value }

    fun getComposePositionState(): State<ComposePosition> = composePositionState

    fun getRefreshContentThreshold(): Float = refreshContentThresholdState.value

    fun getRefreshContentOffset(): Float = refreshContentOffsetState.value

    fun setRefreshState(state: RefreshContentStateEnum) {
        when (state) {
            RefreshContentStateEnum.Stop -> {
                if (refreshContentState.value == RefreshContentStateEnum.Stop)
                    return
                if (!this::coroutineScope.isInitialized)
                    throw IllegalStateException("[RefreshLayoutState]还未初始化完成,请在[LaunchedEffect]中或composable至少组合一次后使用此方法")
                coroutineScope.launch {
                    refreshContentState.value = RefreshContentStateEnum.Stop
                    delay(300)
                    refreshContentOffsetState.animateTo(0f)
                }
            }

            RefreshContentStateEnum.Refreshing -> {
                if (refreshContentState.value == RefreshContentStateEnum.Refreshing)
                    return
                if (!this::coroutineScope.isInitialized)
                    throw IllegalStateException("[RefreshLayoutState]还未初始化完成,请在[LaunchedEffect]中或composable至少组合一次后使用此方法")
                coroutineScope.launch {
                    refreshContentState.value = RefreshContentStateEnum.Refreshing
                    if (canCallRefreshListener)
                        onRefreshListener()
                    else
                        setRefreshState(RefreshContentStateEnum.Stop)
                    animateToThreshold()
                }
            }

            RefreshContentStateEnum.Dragging -> throw IllegalStateException("设置为[RefreshContentStateEnum.Dragging]无意义")
        }
    }

    //偏移量归位,并检查是否超过了刷新阈值,如果超过了执行刷新逻辑
    internal fun offsetHoming() {
        coroutineScope.launch {
            //检查是否进入了刷新状态
            if (abs(refreshContentOffsetState.value) >= refreshContentThresholdState.value) {
                refreshContentState.value = RefreshContentStateEnum.Refreshing
                if (canCallRefreshListener)
                    onRefreshListener()
                else
                    setRefreshState(RefreshContentStateEnum.Stop)
                animateToThreshold()
            } else {
                refreshContentOffsetState.animateTo(0f)
                refreshContentState.value = RefreshContentStateEnum.Stop
            }
        }
    }

    //动画滑动至阈值处
    private suspend fun animateToThreshold() {
        val composePosition = composePositionState.value
        if (composePosition == ComposePosition.Start || composePosition == ComposePosition.Top)
            refreshContentOffsetState.animateTo(refreshContentThresholdState.value)
        else
            refreshContentOffsetState.animateTo(-refreshContentThresholdState.value)
    }

    //增加偏移量
    internal fun offset(refreshContentOffset: Float) {
        coroutineScope.launch {
            val targetValue = refreshContentOffsetState.value + refreshContentOffset
            if (refreshContentState.value != RefreshContentStateEnum.Dragging && targetValue != 0f) {
                refreshContentState.value = RefreshContentStateEnum.Dragging
            }
            refreshContentOffsetState.snapTo(targetValue)
        }
    }
}

@Composable
fun rememberRefreshLayoutState(onRefreshListener: RefreshLayoutState.() -> Unit) =
    remember { RefreshLayoutState(onRefreshListener) }