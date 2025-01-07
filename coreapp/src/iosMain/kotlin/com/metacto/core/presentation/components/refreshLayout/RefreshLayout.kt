package com.metacto.core.presentation.components.refreshLayout

import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.components.refreshLayout.util.ComposePosition
import com.metacto.core.presentation.components.refreshLayout.util.ComposePosition.Bottom
import com.metacto.core.presentation.components.refreshLayout.util.ComposePosition.End
import com.metacto.core.presentation.components.refreshLayout.util.ComposePosition.Start
import com.metacto.core.presentation.components.refreshLayout.util.ComposePosition.Top
import com.metacto.core.presentation.components.refreshLayout.util.runIf
import kotlin.math.roundToInt

@Composable
fun RefreshLayout(
    refreshContent: @Composable RefreshLayoutState.() -> Unit,
    refreshLayoutState: RefreshLayoutState,
    modifier: Modifier = Modifier,
    refreshContentThreshold: Dp? = null,
    composePosition: ComposePosition = Top,
    contentIsMove: Boolean = true,
    dragEfficiency: Float = 0.5f,
    isSupportCanNotScrollCompose: Boolean = false,
    userEnable: Boolean = true,
    refreshingCanScroll: Boolean = false,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    //更新状态
    val orientationIsHorizontal = remember(
        refreshLayoutState,
        composePosition,
        refreshContentThreshold,
        coroutineScope,
        density,
    ) {
        refreshLayoutState.composePositionState.value = composePosition
        refreshLayoutState.coroutineScope = coroutineScope
        if (refreshContentThreshold != null)
            refreshLayoutState.refreshContentThresholdState.value =
                with(density) { refreshContentThreshold.toPx() }
        composePosition.isHorizontal()
    }
    val nestedScrollState = rememberRefreshLayoutNestedScrollConnection(
        composePosition,
        refreshLayoutState,
        dragEfficiency,
        orientationIsHorizontal,
        refreshingCanScroll
    )

    Layout(
        content = {
            if (isSupportCanNotScrollCompose) {
                Box(
                    if (orientationIsHorizontal)
                        Modifier.horizontalScroll(rememberScrollState())
                    else
                        Modifier.verticalScroll(rememberScrollState())
                ) {
                    content()
                }
            } else {
                content()
            }
            refreshLayoutState.refreshContent()
        },
        modifier = modifier
            .runIf(userEnable) {
                nestedScroll(nestedScrollState)
            }
            .clipScrollableContainer(composePosition.orientation)
    ) { measurableList, constraints ->
        val contentPlaceable =
            measurableList[0].measure(constraints.copy(minWidth = 0, minHeight = 0))
        //宽或高不能超过content(根据方向来定)
        val refreshContentPlaceable = measurableList[1].measure(
            Constraints(
                maxWidth = if (orientationIsHorizontal) Constraints.Infinity else contentPlaceable.width,
                maxHeight = if (orientationIsHorizontal) contentPlaceable.height else Constraints.Infinity,
            )
        )
        if (refreshContentThreshold == null && refreshLayoutState.refreshContentThresholdState.value == 0f) {
            refreshLayoutState.refreshContentThresholdState.value =
                if (orientationIsHorizontal) {
                    refreshContentPlaceable.width.toFloat()
                } else {
                    refreshContentPlaceable.height.toFloat()
                }
        }

        layout(contentPlaceable.width, contentPlaceable.height) {
            val offset = refreshLayoutState.refreshContentOffsetState.value.roundToInt()
            when (composePosition) {
                Start -> {
                    contentPlaceable.placeRelative(if (contentIsMove) offset else 0, 0)
                    refreshContentPlaceable.placeRelative(
                        (-refreshContentPlaceable.width) + offset,
                        0
                    )
                }

                End -> {
                    contentPlaceable.placeRelative(if (contentIsMove) offset else 0, 0)
                    refreshContentPlaceable.placeRelative(
                        contentPlaceable.width + offset,
                        0
                    )
                }

                Top -> {
                    contentPlaceable.placeRelative(0, if (contentIsMove) offset else 0)
                    refreshContentPlaceable.placeRelative(
                        0,
                        (-refreshContentPlaceable.height) + offset
                    )
                }

                Bottom -> {
                    contentPlaceable.placeRelative(0, if (contentIsMove) offset else 0)
                    refreshContentPlaceable.placeRelative(
                        0,
                        contentPlaceable.height + offset
                    )
                }
            }
        }
    }
}
